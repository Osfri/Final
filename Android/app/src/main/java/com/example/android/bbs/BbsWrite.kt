package com.example.android.bbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.R

class BbsWrite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_write)

//주석추가 10 04분

        val bbsWriteId = findViewById<TextView>(R.id.bbsWriteId)

        // bbsWriteId.text = MemberDao.user?.id                  글쓰기 db완성시 주석 풀면 됩니다

        val bbsWriteTitle = findViewById<EditText>(R.id.bbsWriteTitle)
        val bbsWriteContent = findViewById<EditText>(R.id.bbsWriteContent)
        val btn_bbsWriteFin = findViewById<Button>(R.id.btn_bbsWriteFin)








        // bbsWrite 글추가완료 버튼
        btn_bbsWriteFin.setOnClickListener {
            println(bbsWriteId.text.toString())
            println(bbsWriteTitle.text.toString())
            println(bbsWriteContent.text.toString())

/*   db쿼리문
            BbsDao.getInstance().bbswrite(BbsDto(0, MemberDao.user?.id, 0, 0, 0,
                title.text.toString(), content.text.toString(), "",
                0, 0))      */

            Toast.makeText(this, "추가되었습니다", Toast.LENGTH_LONG).show()


            val i = Intent(this, BbsActivity::class.java)
            startActivity(i)
        }
    }
}