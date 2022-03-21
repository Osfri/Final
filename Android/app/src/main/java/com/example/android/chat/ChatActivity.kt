package com.example.android.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.android.R
import com.example.android.chat.fragment.AccountFragment
import com.example.android.chat.fragment.ChatFragment
import com.example.android.chat.fragment.PeopleFragment
import com.example.android.databinding.ActivityChatBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatActivity : AppCompatActivity() {
    val binding by lazy{ActivityChatBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 로그인한 회원정보 생성
        ChatSingleton.getInstance().createLoginUserInfo(intent.getStringExtra("loginUserId")!!)

        // 채팅방 하단 네비게이션
        var fragNavi: BottomNavigationView = binding.chatactivityBottomnavigationview

        // 첫진입시 초기화
        setCurrentFragment(PeopleFragment())

        // 하단의 버튼 navi를 클릭했을시
        fragNavi.setOnItemSelectedListener {
            var fr: Fragment?= null
            when(it.itemId){
                R.id.action_people -> {
                    fr = PeopleFragment()
                }
                R.id.action_chat -> {
                    fr = ChatFragment()
                }
                R.id.action_account -> {
                    fr = AccountFragment()
                }
            }
            setCurrentFragment(fr!!)
            true
        }


    }

    fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.chatactivity_framelayout, fragment)
        commit()
    }
}