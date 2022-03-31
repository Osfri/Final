package com.example.android.chat

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.databinding.ActivityGroupMessageBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import com.example.android.R

// 그룹 대화방의 seq
var groupChatRoomSeq: String? = null

// 읽음표시 관련 백키 오류 방지
var groupDatabaseReference: DatabaseReference?= null
var groupValueEventListener: ValueEventListener?= null

class GroupMessageActivity : AppCompatActivity() {
    val binding by lazy{ActivityGroupMessageBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 메시지 입력시 화면 자동올림
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val inputManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // 그룹 대화방의 seq 설정
        groupChatRoomSeq = intent.getStringExtra("selectRoomSeq")

        // 파이어베이스 설정 불러오기
        val myRef = ChatSingleton.getInstance().myRef

        // 전송버튼 클릭시
        binding.groupMessageActivitySendBtn.setOnClickListener {
            // 키보드 올리기
            inputManager.showSoftInput(binding.groupMessageActivityEditMsg, InputMethodManager.SHOW_IMPLICIT)

            // 메시지 모델 생성
            val comment:ChatModel.Comment = ChatModel.Comment()

            // 메시지 정보 저장 (메시지마다 seq로 저장)
            comment.fromId = ChatSingleton.getInstance().loginUserInfo.id
            comment.message = binding.groupMessageActivityEditMsg.text.toString()
            comment.timestamp = ServerValue.TIMESTAMP
            myRef.child(groupChatRoomSeq!!).child("comments").push().setValue(comment)
                .addOnCompleteListener {
                    // 서버저장 성공시 메시지 입력창 초기화
                    binding.groupMessageActivityEditMsg.text.clear()

                    // 키보드 내리기
                    inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
        }

        // 채팅방 제목 설정
        val chatTitle = binding.groupMessageActivitySelectUsers

        // 친구 정보 목록 생성 (검색용)
        val peopleMap:MutableMap<String, ChatUserDto> = ChatSingleton.getInstance().peopleList

        ChatSingleton.getInstance().myRef.child(groupChatRoomSeq!!).child("users").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var users:String = ""
                    for(item: DataSnapshot in snapshot.children){
                        if(item.key.equals(ChatSingleton.getInstance().loginUserInfo.id)) continue  // 자신은 제외

                        // 단체채팅방에 참여된 사람만 포함
                        if(item.value == true){
                            users += (peopleMap.getValue(item.key!!).name + ", ")
                        }
                    }
                    chatTitle.text = users.substring(0,users.length-2)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }

        )

        // 리사이클러뷰 작동
        val recyclerView: RecyclerView = binding.groupMessageActivityRecyclerView
        val groupMessageRecyclerViewAdapter: GroupMessageRecyclerViewAdapter = GroupMessageRecyclerViewAdapter()
        groupMessageRecyclerViewAdapter.getMessageList(recyclerView)
        recyclerView.adapter = groupMessageRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // 채팅방 나가기 버튼 클릭시
        binding.groupMessageActivityChatExitBtn.setOnClickListener {
            val modifyExit:MutableMap<String, Any> = mutableMapOf()
            modifyExit.put(ChatSingleton.getInstance().loginUserInfo.id, false)
            ChatSingleton.getInstance().myRef.child(groupChatRoomSeq!!).child("users")
                .updateChildren(modifyExit).addOnCompleteListener {
                    // 데이터베이스 상에서 읽음표시 지속 방지
                    // valueEventListener!=null 도 가능 (채팅방이 없으면 valueEventListener가 null이므로)
                    if(groupChatRoomSeq != null ){
                        groupDatabaseReference!!.removeEventListener(groupValueEventListener!!)
                    }

                    // 전역값 초기화
                    groupChatRoomSeq = null
                    groupDatabaseReference = null
                    groupValueEventListener = null
                    chatUsersCount = 0

                    // 액티비티 삭제
                    finish()

                    // 액티비티 사라지는 효과
                    overridePendingTransition(R.anim.fromleft, R.anim.fromleft_toright) // finish 밑에 있어야 작동
                }
        }
    }


    // 채팅방 안에 메시지를 표시하는 아답터
    class GroupMessageRecyclerViewAdapter()
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        // 메시지들에 대한 관련 정보 저장
        val comments:MutableList<ChatModel.Comment> = mutableListOf()

        // 채팅방의 메시지 내용 불러오기
        fun getMessageList(recyclerView: RecyclerView){
            groupDatabaseReference = ChatSingleton.getInstance().myRef.child(groupChatRoomSeq!!).child("comments")
            groupValueEventListener = groupDatabaseReference!!.addValueEventListener(
                object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        comments.clear()    // 중복데이터 저장 방지
                        val readUsersMap:MutableMap<String, Any> = mutableMapOf()   // <메시지의 키, 키의 정보>

                        // 메시지들의 seq를 가져오기(item)
                        for(item: DataSnapshot in snapshot.children){
                            val key:String = item.key!!

                            // 메시지 읽기 -> 읽음 표시 -> 서버 데이터 수정 -> 메시지 읽기 ... 반복 방지
                            // 메시지를 읽었을때와 읽지않았을 때를 구분
                            val commentOrigin:ChatModel.Comment = item.getValue(ChatModel.Comment::class.java)!!
                            val commentModify:ChatModel.Comment = item.getValue(ChatModel.Comment::class.java)!!
                            commentModify.readUsers.put(ChatSingleton.getInstance().loginUserInfo.id, true)   // 자신이 읽었음을 추가

                            readUsersMap.put(key, commentModify)  // 자신이 읽었음을 추가한 내용을 저장
                            comments.add(commentOrigin)
                        }

                        if(comments.size != 0){
                            // 댓글목록 마지막에 읽은 사람중 자신이 있는지 확인
                            if(comments.get(comments.size - 1).readUsers.containsKey(ChatSingleton.getInstance().loginUserInfo.id)){
                                notifyDataSetChanged()  // 리사이클러뷰 데이터 갱신
                                recyclerView.scrollToPosition(comments.size - 1)    // 작성한 글(맨 밑으로)로 이동
                            }else{
                                // 해당 메시지의 seq에 자신이 읽었음을 갱신
                                ChatSingleton.getInstance().myRef.child(groupChatRoomSeq!!).child("comments").updateChildren(readUsersMap).addOnCompleteListener {
                                    notifyDataSetChanged()  // 리사이클러뷰 데이터 갱신
                                    recyclerView.scrollToPosition(comments.size - 1)    // 작성한 글(맨 밑으로)로 이동
                                }
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) { }
                }
            )
        }

        class groupMessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val message = itemView.findViewById<TextView>(R.id.chatmessage_msg)
            val face = itemView.findViewById<ImageView>(R.id.chatmessage_face)
            var textWrap = itemView.findViewById<LinearLayout>(R.id.chatmessage_wrap)
            var container = itemView.findViewById<LinearLayout>(R.id.chatmessage_container)
            val timeStampRight = itemView.findViewById<TextView>(R.id.chatmessage_timestamp_right)
            val timeStampLeft = itemView.findViewById<TextView>(R.id.chatmessage_timestamp_left)
            val readCountLeft = itemView.findViewById<TextView>(R.id.chatmessage_readcounter_left)
            val readCountRight = itemView.findViewById<TextView>(R.id.chatmessage_readcounter_right)
            val sendUserName = itemView.findViewById<TextView>(R.id.chatmessage_username)

            fun messageViewHolder(msg:String, dir:String, time:Long, fromId:String){
                var visible:Int
                var sort:Int
                var timeStampview: TextView

                if(dir.equals("R")){
                    visible = View.INVISIBLE
                    sort = Gravity.RIGHT
                    timeStampview = timeStampLeft
                }else{
                    visible = View.VISIBLE
                    sort = Gravity.LEFT
                    timeStampview = timeStampRight
                }

                val backgroundDirection:Int = if(dir.equals("R")) R.drawable.message_right else R.drawable.message_left
                val textColor:Int = if(dir.equals("R")) Color.WHITE else Color.WHITE
                message.text = msg
                message.setBackgroundResource(backgroundDirection)  // 말풍선 이미지 나인패치 적용(파일명.9.확장자) (크기에 따라 자동으로 늘어남)
                message.setTextColor(textColor)
                face.visibility = visible
                textWrap.gravity = sort
                container.gravity = sort
                sendUserName.text = ChatSingleton.getInstance().getLoginUserInfo(fromId).name
                sendUserName.visibility = visible

                // 값 초기화 후 설정
                timeStampRight.text = ""
                timeStampLeft.text = ""
                timeStampview.text = changeTimeStamp(time)
                timeStampview.visibility = View.VISIBLE
                timeStampview.gravity = Gravity.BOTTOM
            }

            // 데이터베이스 시간 변환
            fun changeTimeStamp(time:Long):String{
                // Locale.getDefault()
                // simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
                val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
                val changeTime: Date = Date(time)
                return simpleDateFormat.format(changeTime)
            }
        }


        // 해당 채팅방에 속한 user의 수를 설정
        fun setReadCounter(position:Int, countTextView: TextView){
            if(chatUsersCount == 0){
                ChatSingleton.getInstance().myRef.child(groupChatRoomSeq!!).child("users").addListenerForSingleValueEvent(
                    object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // 채팅방에 있는 유저 저장
                            val chatUsers:MutableMap<String, Boolean> = snapshot.getValue() as MutableMap<String, Boolean>

                            // 메시지 카운터
                            chatUsersCount = chatUsers.size
                            var count = chatUsersCount - comments[position].readUsers.size  // 전체인원수 - 읽은 사람 수

                            if(count>0){
                                countTextView.visibility = View.VISIBLE
                                countTextView.text = count.toString()
                            }else{
                                countTextView.visibility = View.INVISIBLE
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    }
                )
            }else{
                // 메시지 카운터
                var count = chatUsersCount - comments[position].readUsers.size  // 전체인원수 - 읽은 사람 수

                if(count>0){
                    countTextView.visibility = View.VISIBLE
                    countTextView.text = count.toString()
                }else{
                    countTextView.visibility = View.INVISIBLE
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
            return groupMessageViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            // R: 내가 보낸 메시지, L: 상대방이 보낸 메시지
            val direction:String = if(comments[position].fromId.equals(ChatSingleton.getInstance().loginUserInfo.id)) "R" else "L"
            val time:Long = comments[position].timestamp as Long

            // 읽음 표시 값 초기화
            (holder as groupMessageViewHolder).readCountLeft.text = ""
            (holder as groupMessageViewHolder).readCountRight.text = ""

            // 읽음 표시
            if(direction.equals("R")){
                setReadCounter(position, (holder as groupMessageViewHolder).readCountLeft)
            }else{
                setReadCounter(position, (holder as groupMessageViewHolder).readCountRight)
            }

            // 바인딩
            (holder as groupMessageViewHolder).messageViewHolder(comments[position].message, direction, time, comments[position].fromId)
        }

        override fun getItemCount(): Int {
            return comments.size
        }

    }

    // 채팅방 안에서 뒤로가기를 눌렀을 때
    override fun onBackPressed() {
        // 데이터베이스 상에서 읽음표시 지속 방지
        // valueEventListener!=null 도 가능 (채팅방이 없으면 valueEventListener가 null이므로)
        if(groupChatRoomSeq != null ){
            groupDatabaseReference!!.removeEventListener(groupValueEventListener!!)
        }

        // 전역값 초기화
        groupChatRoomSeq = null
        groupDatabaseReference = null
        groupValueEventListener = null
        chatUsersCount = 0

        // 액티비티 삭제
        finish()

        // 액티비티 사라지는 효과
        overridePendingTransition(R.anim.fromleft, R.anim.fromleft_toright) // finish 밑에 있어야 작동
    }
}