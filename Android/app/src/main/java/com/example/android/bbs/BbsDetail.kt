package com.example.finalproject.bbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.finalproject.R

class BbsDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_detail)


        val data = intent.getParcelableExtra<BbsDto>("data")
        println("DDDDDDDDDDDDDDDDDDDDDD--------------------------------------${data!!.seq}")

/*
        val dto = BbsDao.getInstance().bbsDetail(data!!.seq)
        println(data?.id ) // aaa 넘어갈것
*/

        // 디테일 속 정보 표시
        val bbsDetailId = findViewById<TextView>(R.id.bbsDetailId)
        val bbsDetailCount = findViewById<TextView>(R.id.bbsDetailCount)
        val bbsDetailTitle = findViewById<TextView>(R.id.bbsDetailTitle)
        val bbsDetailContent = findViewById<TextView>(R.id.bbsDetailContent)


/*

        bbsDetailId.text = dto?.id
        bbsDetailCount.text = dto?.readcount.toString()
        bbsDetailTitle.text = dto?.title
        bbsDetailContent.text = dto?.content

*/






        // bbsDetail -> Bbs 로 이동
        val bbsDetailBack = findViewById<Button>(R.id.bbsDetailBack)
        bbsDetailBack.setOnClickListener {
            val i = Intent(this, Bbs::class.java)
            startActivity(i)
        }
    }
}