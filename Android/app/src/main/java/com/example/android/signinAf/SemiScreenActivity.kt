package com.example.android.signinAf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.R
import com.example.android.bbs.BbsWrite

class SemiScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semi_screen)

        val semiSc_Btn_Manager = findViewById<Button>(R.id.semiSc_Btn_Manager)      // 관리자버튼
        val semiSc_Btn_Invidual = findViewById<Button>(R.id.semiSc_Btn_Invidual)    // 개인버튼


        // 관리자 확인 페이지이동
        semiSc_Btn_Manager.setOnClickListener {
            val i = Intent(this, SemiManagementActivity::class.java)
            startActivity(i)
        }
        // 개인 확인 페이지 이동
        semiSc_Btn_Invidual.setOnClickListener {
            val i = Intent(this, SemiIndividualActivity::class.java)
            startActivity(i)
        }
    }
}