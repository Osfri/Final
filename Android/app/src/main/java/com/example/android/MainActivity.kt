package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.chat.ChatSingleton
import com.example.android.firstscreen.firstscreenActivity
import com.example.android.lunch.FoodActivity
import com.example.android.manager.ManagerActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.manager.bbs.ManagerBbsActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity

class MainActivity : AppCompatActivity() {
    override fun onBackPressed() {
        val i = Intent(this,SigninActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)                      // 로그인
//        val btnFirstScreen = findViewById<Button>(R.id.btnFirstScreen)          // 관리자,개인 구분창
        val btnBbs = findViewById<Button>(R.id.btnBbs)                          // 공지사항,건의사항
        val btnCalender = findViewById<Button>(R.id.btnCalender)                // 일정표
        val btnLunch = findViewById<Button>(R.id.btnLunch)                      // 식단표
        val btnHoliday = findViewById<Button>(R.id.btnHoliday)                  // off 휴일 신청
        val btnPointMall = findViewById<Button>(R.id.btnPointMall)              // 포인트몰
        val btnChat = findViewById<Button>(R.id.btnChat)                        // 채팅
        val btnAlarm = findViewById<Button>(R.id.btnAlarm)                      // 알람
        val btnPhoneNumber = findViewById<Button>(R.id.btnPhoneNumber)          // 연락처
        val btnManager = findViewById<Button>(R.id.btnManager)

        btnLogin.visibility=View.GONE
        if (MemberDao.user?.auth != 0 && MemberDao.user?.auth != 3){
            btnManager.visibility=View.INVISIBLE
        }


        // 로그인
        btnLogin.setOnClickListener {
            val i = Intent(this, SigninActivity::class.java)
            startActivity(i)
        }
        // 관리자,개인 구분창
/*        btnFirstScreen.setOnClickListener {
            val i = Intent(this,firstscreenActivity::class.java)
            startActivity(i)
        }*/
        // 공지사항,건의사항
        btnBbs.setOnClickListener {
            val i = Intent(this, BbsActivity::class.java)
            startActivity(i)
        }
        // 일정표
        btnCalender.setOnClickListener {
            val i = Intent(this, CalendarActivity::class.java)
            startActivity(i)
        }
        // (수정,추가_백엔드) 식단표 (로그인 정보로 접속)
        btnLunch.setOnClickListener {
            if(MemberDao.user != null){
                val i = Intent(this, FoodActivity::class.java)
                startActivity(i)
            }
        }
        // off 휴일 신청
        btnHoliday.setOnClickListener {
            val i = Intent(this,OffDayActivity::class.java)
            startActivity(i)
        }
        // (수정,추가_백엔드) 포인트몰 (로그인 정보로 접속)
        btnPointMall.setOnClickListener {
            // (수정,추가_백엔드) 로그인했을시에만 동작
            if(MemberDao.user != null){
                val i = Intent(this,PointMallActivity::class.java)
                startActivity(i)
            }
        }
        // (수정,추가_백엔드) 채팅 (로그인 정보로 접속)
        btnChat.setOnClickListener {
            // (수정,추가_백엔드) 로그인했을시에만 동작
            if(MemberDao.user != null){
                val i = Intent(this,ChatActivity::class.java)
                startActivity(i)
            }
        }
        // 알람푸쉬
        btnAlarm.setOnClickListener{
        val i = Intent(this,AlarmActivity::class.java)
        startActivity(i)
            }

        // (수정,추가_백엔드) 연락처 (로그인 정보로 접속)
        btnPhoneNumber.setOnClickListener {
            // (수정,추가_백엔드) 로그인했을시에만 동작
            if(MemberDao.user != null){
                val i = Intent(this,PhoneNumActivity::class.java)
                startActivity(i)
            }
        }
        btnManager.setOnClickListener {
            if(MemberDao.user?.auth == 3){
                val i = Intent(this,ManagerBbsActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }else{
                val i = Intent(this,ManagerMenuActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
    }
}