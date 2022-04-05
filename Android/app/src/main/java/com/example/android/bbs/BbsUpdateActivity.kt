package com.example.android.bbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.android.R
import com.example.android.manager.BoardtypeDto
import com.example.android.signin.MemberDao

class BbsUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_update)

        val data = intent.getParcelableExtra<BbsDto>("data")
        val bbstype = intent?.getParcelableExtra<BoardtypeDto>("dataType")

        val updateBtn = findViewById<Button>(R.id.btn_bbsUpdateFin)
        val updateTitle = findViewById<EditText>(R.id.bbsUpdateTitle)
        val updateContent = findViewById<EditText>(R.id.bbsUpdateContent)
        val updateText = findViewById<TextView>(R.id.bbsUpdateId)

        var title = ""
        var content = ""
        val typename = if (BbsActivity.typename == ""){"공지사항"}else{BbsActivity.typename}
        updateText.text = "작성자: ${MemberDao.user!!.id} 게시판:${typename}"

        updateTitle.setText("${data?.title}")
        updateContent.setText("${data?.content}")

        updateTitle.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                title = updateTitle.text.toString()
            }
        })
        updateContent.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                content = updateContent.text.toString()
            }
        })

        updateBtn.setOnClickListener {
            val dto = BbsDto(data!!.seq,MemberDao.user?.id,title,content,0,data.wdate,0,data.type,data.code,0,data.gr,"")
            BbsDao.getInstance().updateBbs(dto)
            startActivity(Intent(this,BbsActivity::class.java))
        }


    }
}