package com.example.finalproject.bbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class Bbs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs)


        // bbs리스트
        var bbslistRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)  // bbsRecyclerView 변수

        val bbslist = BbsDao.getInstance().getBbsList()
        println(bbslist[0].title)

        val mAdapter = CustomAdapterBbsList(this, bbslist)
        bbslistRecyclerView.adapter = mAdapter

        val layout = LinearLayoutManager(this)
        bbslistRecyclerView.layoutManager = layout
        bbslistRecyclerView.setHasFixedSize(true)
        



        
        // bbs -> bbsWrite 이동    (글쓰기로 가는 버튼)
        val btn_bbsListWrite = findViewById<Button>(R.id.btn_bbsListWrite)
        btn_bbsListWrite.setOnClickListener {
            val i = Intent(this, BbsWrite::class.java)
            startActivity(i)
        }
    }
}