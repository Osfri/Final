package com.example.android.signinAf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.R

class SemiIndividualActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semi_individual)


        val semiInvi_Btn_HospitalCode = findViewById<Button>(R.id.semiInvi_Btn_HospitalCode)      // 개인 코드등록 버튼


        // 코드등록 후 승인대기 페이지로 이동
        semiInvi_Btn_HospitalCode.setOnClickListener {
            val i = Intent(this, SemiWaitingActivity::class.java)
            startActivity(i)
        }
    }
}