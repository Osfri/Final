package com.example.android.offday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import com.example.android.R

class OffDayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_off_day)


        val offCalendarView = findViewById<CalendarView>(R.id.offCalendarView)      // 달력
        val offTextView = findViewById<TextView>(R.id.offTextView)                  // 오프 등록시 날짜 누르면 textview에 목록 표시
        val offSaveBtn = findViewById<Button>(R.id.offSaveBtn)                      // 오프 등록 버튼


    }
}