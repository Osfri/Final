package com.example.android.chat.fragment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.chat.*
import com.example.android.databinding.ActivitySelectPeopleBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SelectPeopleActivity : AppCompatActivity() {
    val binding by lazy{ActivitySelectPeopleBinding.inflate(layoutInflater)}

    // 대화 참가자 저장용도
    var chatModel: ChatModel = ChatModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 친구 목록 생성
        val peopleMap:MutableMap<String, ChatUserDto> = ChatSingleton.getInstance().getChatPeopleList()
        var peopleList:MutableList<ChatUserDto> = mutableListOf()
        for (dto:ChatUserDto in peopleMap.values){
            peopleList.add(dto)
        }

        // 리사이블러 뷰
        val recyclerView: RecyclerView = binding.selectPeopleActivityRecyclerview
        recyclerView.adapter = SelectPeopleFragmentRecyclerViewAdapter(this, chatModel, peopleList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 초기화
        groupChatRoomSeq = null

        // 방만들기 버튼 클릭시
        binding.selectPeopleActivityChatCreateBtn.setOnClickListener {
            chatModel.users.put(ChatSingleton.getInstance().loginUserInfo.id, true)

            var nextIntent: Intent


            // 1:1대화일 경우
            if(chatModel.users.keys.size==2){
                chatModel.users.remove(ChatSingleton.getInstance().loginUserInfo.id)
                val chatModelUsers:MutableSet<String> = chatModel.users.keys
                chatModelUsers.remove(ChatSingleton.getInstance().loginUserInfo.id)
                val selectUser:String = chatModelUsers.first()
                nextIntent = Intent(this, MessageActivity::class.java)
                nextIntent.putExtra("selectUser", selectUser)
                startActivity(nextIntent)
            }else{
                checkGroupChatRoom()
            }
        }
    }

    fun checkGroupChatRoom(){
        // addListenerForSingleValueEvent: 1번만 체크
        // 자신이 속한 방 seq를 정렬
        ChatSingleton.getInstance().myRef.orderByChild("users/"+ChatSingleton.getInstance().loginUserInfo.id).equalTo(true).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    check@ for(item: DataSnapshot in snapshot.children){
                        // item(방 seq)의 value를 꺼내서 ChatModel클래스로 캐스팅
                        val chatmodel: ChatModel = item.getValue(ChatModel::class.java)!!
                        // 선택한 대화상대가 모두 포함된 방 찾기 (chatModel: 채팅방 seq, chatmodel: 꺼내온 채팅방 seq)
                        if(chatmodel.users.keys.size == chatModel.users.keys.size){
                            for(user:String in chatModel.users.keys){
                                // 선택한 대화상대가 없을 경우 다음 방 seq로 이동
                                if(!chatmodel.users.containsKey(user)) continue@check
                            }
                            // 선택한 대화상대가 모두 포함된 방 찾음
                            groupChatRoomSeq = item.key!!
                            break
                        }else{
                            continue
                        }
                    }


                    var nextIntent: Intent
                    if(groupChatRoomSeq.equals(null)){
                        // 선택한 대화상대로 만든 방이 없을경우
                        groupChatRoomSeq = ChatSingleton.getInstance().myRef.push().key!!
                        ChatSingleton.getInstance().myRef.child(groupChatRoomSeq!!).setValue(chatModel)
                    }
                    nextIntent = Intent(this@SelectPeopleActivity, GroupMessageActivity::class.java)
                    nextIntent.putExtra("selectRoomSeq", groupChatRoomSeq)
                    startActivity(nextIntent)
                }
                override fun onCancelled(error: DatabaseError) {  }
            }
        )
    }

    // PeopleFragment에서 불러옴
    // 리사이클러 뷰
    class SelectPeopleFragmentRecyclerViewAdapter(val context: Context, val chatModel:ChatModel, val peopleList:MutableList<ChatUserDto>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            private val userName = itemView.findViewById<TextView>(R.id.chatfriend_user_name)
            private val userPhoneNumber = itemView.findViewById<TextView>(R.id.chatfriend_user_phone)
            private val checkBox = itemView.findViewById<CheckBox>(R.id.chatfriend_select_checkbox)

            fun itemViewHolder(dataVO: ChatUserDto, context: Context, chatModel:ChatModel) {
                // 데이터 셋팅
                userName.text = dataVO.name
                userPhoneNumber.text = dataVO.phonenumber

                checkBox.setOnCheckedChangeListener { compoundButton, b ->
                    if(b){  // 체크 되었을 경우
                        chatModel.users.put(dataVO.id, true)
                    }else{  // 체크 되지 않았을 경우
                        chatModel.users.remove(dataVO.id)
                    }
                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_friend_select, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ItemViewHolder).itemViewHolder(peopleList[position], context, chatModel)

        }

        override fun getItemCount(): Int {
            return peopleList.size
        }
    }

    // 채팅방 생성 후 뒤로가기 누를시 선택화면 나타나지 않도록 함
    override fun onPause() {
        super.onPause()
        finish()
    }

    // 채팅방 생성 후 뒤로가기 누를 시 초기화
    override fun onBackPressed() {
        super.onBackPressed()
        groupChatRoomSeq = null
    }
}