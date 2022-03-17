package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.firstscreen.firstscreenActivity
import com.example.android.lunch.FoodActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.signinActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)                      // 로그인
        val btnFirstScreen = findViewById<Button>(R.id.btnFirstScreen)          // 관리자,개인 구분창
        val btnBbs = findViewById<Button>(R.id.btnBbs)                          // 공지사항,건의사항
        val btnCalender = findViewById<Button>(R.id.btnCalender)                // 일정표
        val btnLunch = findViewById<Button>(R.id.btnLunch)                      // 식단표
        val btnHoliday = findViewById<Button>(R.id.btnHoliday)                  // off 휴일 신청
        val btnPointMall = findViewById<Button>(R.id.btnPointMall)              // 포인트몰
        val btnChat = findViewById<Button>(R.id.btnChat)                        // 채팅



        // 로그인
        btnLogin.setOnClickListener {
            val i = Intent(this, signinActivity::class.java)
            startActivity(i)
        }
        // 관리자,개인 구분창
        btnFirstScreen.setOnClickListener {
            val i = Intent(this,firstscreenActivity::class.java)
            startActivity(i)
        }
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
        // 식단표
        btnLunch.setOnClickListener {
            val i = Intent(this,FoodActivity::class.java)
            startActivity(i)
        }
        // off 휴일 신청
        btnHoliday.setOnClickListener {
            val i = Intent(this,OffDayActivity::class.java)
            startActivity(i)
        }
        // 포인트몰
        btnPointMall.setOnClickListener {
            val i = Intent(this,PointMallActivity::class.java)
            startActivity(i)
        }
        // 채팅
        btnChat.setOnClickListener {
            val i = Intent(this,ChatActivity::class.java)
            startActivity(i)
        }



    }
}