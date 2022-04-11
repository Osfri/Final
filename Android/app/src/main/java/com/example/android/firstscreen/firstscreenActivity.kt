package com.example.android.firstscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.android.R
import com.example.android.signin.SigninActivity
import com.example.android.signinAf.SemiScreenActivity
import com.example.android.signinAf.SemiWaitingActivity
import com.example.android.signup.SignupActivity

class firstscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)
/*

        val signin = findViewById<Button>(R.id.btn_signin1)
        val signup = findViewById<ImageButton>(R.id.btn_signup1)

        // 지워야 할 페이지 관리자 / 개인 선택창  임시연결

        // 임시 가입 후 관리자 개인 선택창
        signin.setOnClickListener {
            val i = Intent(this,SemiScreenActivity::class.java)
            startActivity(i)
        }
        // 임시  개인 코드등록 후 웨이팅 화면
        signup.setOnClickListener {
            val i = Intent(this,SemiWaitingActivity::class.java)
            startActivity(i)
        }
*/


    }
}