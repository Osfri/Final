package com.example.android.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.lunch.CustomAdapterFood
import com.example.android.lunch.FoodDto

class CalendarActivity : AppCompatActivity() {

    // 데이터 확인용 변수로 지워도 됩니다
    var userList = arrayListOf<CalendarDto>(
        CalendarDto("againsa", "2022-06-08", "오후","2일 휴무"),
        CalendarDto("bstro", "2022-08-08", "새벽","1일 휴무"),
        CalendarDto("cvbk", "2023-02-08", "오전","1일 휴무"),

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calTitleTextView = findViewById<TextView>(R.id.calTitleTextView)             // 페이지 제목
        val calCalendarView = findViewById<CalendarView>(R.id.calCalendarView)           // 달력 form
        val calContentEditText = findViewById<EditText>(R.id.calContentEditText)         // 일정 내용 입력란
        val calSaveBtn = findViewById<Button>(R.id.calSaveBtn)                           // 저장 버튼



        var calRecyclerView = findViewById<RecyclerView>(R.id.calRecyclerView)           // 달력 일정 입력시 리사이클러뷰 목록 표시

        val mAdapter = CustomAdapterCal(this, userList)
        calRecyclerView.adapter = mAdapter

        var layout = LinearLayoutManager(this)
        calRecyclerView.layoutManager = layout

        calRecyclerView.setHasFixedSize(true)


    }
}