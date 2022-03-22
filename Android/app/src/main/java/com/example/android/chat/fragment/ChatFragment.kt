package com.example.android.chat.fragment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.chat.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

// 채팅목록 리스트 업데이트 관련 백키 오류 방지
var chatListUpdateValueEventListner: ValueEventListener?= null

class ChatFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(R.layout.fragment_chat, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.chatfragment_recyclerview)

        // 전역변수 초기화
        chatListUpdateValueEventListner = null

        // 리사이클러뷰 작동
        val recyclerAdapter = ChatFragmentRecyclerViewAdapter(inflater.context)
        recyclerAdapter.getMessageList(recyclerView)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        return view
    }

    // 채팅방에서 목록으로 되돌아올경우 리사클러뷰 갱신
    override fun onResume() {
        super.onResume()
        var recyclerView = view?.findViewById<RecyclerView>(R.id.chatfragment_recyclerview)
        val recyclerAdapter = ChatFragmentRecyclerViewAdapter(view?.context!!)
        recyclerAdapter.getMessageList(recyclerView!!)
        recyclerView.adapter = recyclerAdapter
    }

    // 리사이클러뷰
    class ChatFragmentRecyclerViewAdapter(val context: Context)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        // 준비사항
        var chatsList:MutableList<ChatModel> = mutableListOf()
        var chatUsersList:MutableList<String> = mutableListOf() // 채팅방의 대화 상대모음
        var chatRoomSeq:MutableList<String> = mutableListOf()   // 채팅방의 seq 저장


        fun getMessageList(recyclerView: RecyclerView){
            // 채팅방 목록 업데이트 확인
            chatListUpdateValueEventListner = ChatSingleton.getInstance().myRef.orderByChild("users/"+ ChatSingleton.getInstance().loginUserInfo.id).equalTo(true).addValueEventListener(
                object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatsList.clear()    // 중복데이터 저장 방지
                        for(item: DataSnapshot in snapshot.children){
                            // item의 value를 꺼내서 ChatModel클래스로 캐스팅
                            val chatmodel: ChatModel = item.getValue(ChatModel::class.java)!!
                            chatsList.add(chatmodel)
                            chatRoomSeq.add(item.key!!)
                        }
                        notifyDataSetChanged()  // 리사이클러뷰 데이터 갱신
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )

        }



        class ChatListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val chatRoomTitle: TextView = itemView.findViewById<TextView>(R.id.chatitem_title)
            val lastMsgTitle: TextView = itemView.findViewById<TextView>(R.id.chatitem_lastmsg)
            val lastMsgTime: TextView = itemView.findViewById<TextView>(R.id.chatitem_lastmsg_time)

            fun ChatListViewHolder(title:String, lastMsg:String, chatRoom:ChatModel, chatRoomUsers:String, context: Context, lastTime:Long, chatRoomSeq:String){
                // chatRoom: 채팅방의 seq
                chatRoomTitle.text = title
                lastMsgTitle.text = lastMsg
                lastMsgTime.text = if(lastTime > 0) changeTimeStamp(lastTime) else ""

                // 채팅방 목록에서 채팅 클릭시 해당채팅방으로 이동
                itemView.setOnClickListener {
                    var chatIntent: Intent


                    if(chatRoom.users.keys.size>2){ // 채팅방이 단체(3명 이상)으로 되어 있는 경우 (자신포함)
                        chatIntent = Intent(itemView.context, GroupMessageActivity::class.java)

                        chatIntent.putExtra("selectRoomSeq", chatRoomSeq)
                    }else{
                        chatIntent = Intent(itemView.context, MessageActivity::class.java)

                        chatIntent.putExtra("selectUser", chatRoomUsers)    // MessageActivity와 연관 (name값 유의)
                    }


                    val activityOptions: ActivityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fromright, R.anim.fromright_toleft)
                    context.startActivity(chatIntent,activityOptions.toBundle())

                }
            }

            fun changeTimeStamp(time:Long):String{
                // Locale.getDefault()
                // simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
                val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
                val changeTime: Date = Date(time)
                return simpleDateFormat.format(changeTime)
            }



        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_chat, parent, false)
            return ChatListViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            // 친구 정보 목록 생성 (검색용)
            val peopleMap:MutableMap<String, ChatUserDto> = ChatSingleton.getInstance().peopleList


            // 채팅방 목록 제목 갱신 (단체방에서 개인만 남을시 개인채팅방 이름과 동일하게 됨)
            var chatRoomUserSize:Int = 0
            for(user:MutableMap.MutableEntry<String, Boolean> in chatsList[position].users){
                if(user.value == true){
                    chatRoomUserSize++
                }
            }

            // 채팅방 목록의 채팅방명 지정
            var chatUsers:String =
                //if(chatsList[position].users.keys.size -1 > 1){
                if(chatRoomUserSize -1 > 1){
                    var chatTitleTemp:MutableSet<String> = mutableSetOf()
                    chatTitleTemp.addAll(chatsList[position].users.keys)
                    chatTitleTemp.remove(ChatSingleton.getInstance().loginUserInfo.id)  // 자신은 제외
                    //  "${peopleMap.getValue(chatTitleTemp.first()).name}님 외 ${chatsList[position].users.keys.size - 2}명"  // 제목에 포함된 사람은 제외
                    "${peopleMap.getValue(chatTitleTemp.first()).name}님 외 ${chatRoomUserSize - 2}명"  // 제목에 포함된 사람은 제외
                }else{
                    var chatTitleTemp:MutableSet<String> = mutableSetOf()
                    chatTitleTemp.addAll(chatsList[position].users.keys)
                    chatTitleTemp.remove(ChatSingleton.getInstance().loginUserInfo.id)  // 자신은 제외
                    "${peopleMap.getValue(chatTitleTemp.first()).name}님"
                }

            // 채팅방에 있는 유저 체크
            for(chatuser:String in chatsList[position].users.keys){
                // 채팅방에서 자신은 제외
                if(!chatuser.equals(ChatSingleton.getInstance().loginUserInfo.id)){
                    chatUsersList.add(chatuser)
                }
            }
            // 마지막 메시지 체크 (java TreepMap 사용: 키값을 정렬하기 위함)(재정렬 방지)(내림차순정렬-> 최근메시지는 가장 첫번째)
            var commentsMap: TreeMap<String, ChatModel.Comment> = TreeMap<String, ChatModel.Comment>(Collections.reverseOrder())
            var lastMsgKey:String = ""
            var lastMsg:String = ""
            var lastMsgTime:Long = 0


            if(chatsList[position].comments.keys.size > 0){
                commentsMap.putAll(chatsList[position].comments)
                lastMsgKey = commentsMap.keys.toTypedArray()[0]    // 댓글모음에서 최근 댓글모음의 첫번째 키를 가져옴
                lastMsg = chatsList[position].comments[lastMsgKey]!!.message    // 최근 댓글모음에서 메시지를 가져옴
                lastMsgTime = chatsList[position].comments[lastMsgKey]!!.timestamp as Long
            }
            // 바인딩
            (holder as ChatListViewHolder).ChatListViewHolder(chatUsers+"과 채팅방", lastMsg, chatsList[position], chatUsersList[position],context, lastMsgTime, chatRoomSeq[position])



        }

        override fun getItemCount(): Int {
            return chatsList.size
        }
    }
}