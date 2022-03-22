package com.example.android.chat.fragment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.chat.ChatSingleton
import com.example.android.chat.ChatUserDto
import com.example.android.chat.MessageActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PeopleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 친구 목록 생성
        val peopleMap:MutableMap<String, ChatUserDto> = ChatSingleton.getInstance().getChatPeopleList()
        var peopleList:MutableList<ChatUserDto> = mutableListOf()
        for (dto:ChatUserDto in peopleMap.values){
            peopleList.add(dto)
        }


        var view:View = inflater.inflate(R.layout.fragment_people, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.peoplefragment_recyclerview)
        recyclerView.adapter = PeopleFragmentRecyclerViewAdapter(inflater.context, peopleList)
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)

        val floatingActionButton: FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.peoplefragment_floatingActionButton)
        floatingActionButton.setOnClickListener {
            val nextIntent = Intent(view.context,SelectPeopleActivity::class.java)
            startActivity(nextIntent)
        }




        return view
    }

    // 리사이클러 뷰 어댑터
    class PeopleFragmentRecyclerViewAdapter(val context: Context, val peopleList:MutableList<ChatUserDto>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            private val userName: TextView = itemView.findViewById<TextView>(R.id.chatfriend_user_name)
            private val userPhoneNumber: TextView = itemView.findViewById<TextView>(R.id.chatfriend_user_phone)

            fun itemViewHolder(dataVO: ChatUserDto, context: Context) {
                // 데이터 셋팅
                userName.text = dataVO.name
                userPhoneNumber.text = dataVO.phonenumber
                userName.setOnClickListener {
                    val nextIntent = Intent(context, MessageActivity::class.java)
                    // 선택한 대상의 아이디 전송
                    nextIntent.putExtra("selectUser", dataVO.id)
                    val activityOptions: ActivityOptions = ActivityOptions.makeCustomAnimation(context,R.anim.fromright, R.anim.fromright_toleft)
                    context.startActivity(nextIntent,activityOptions.toBundle())
                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_friend, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ItemViewHolder).itemViewHolder(peopleList[position], context)

        }

        override fun getItemCount(): Int {
            return peopleList.size
        }
    }
}