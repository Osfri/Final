package com.example.android.signinAf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.MainActivity
import com.example.android.R

class SemiManagementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semi_management)

        val semiMana_Btn_Finish = findViewById<Button>(R.id.semiMana_Btn_Finish)      // 관리자 등록 버튼
        val semiMana_Btn_Address = findViewById<Button>(R.id.semiMana_Btn_Address)                       // 병원주소 찾기 버튼


        // 관리자 등록 후 메인페이지 이동
        semiMana_Btn_Finish.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }
}