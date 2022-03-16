package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.bbs.Bbs
import com.example.android.firstscreen.firstscreenActivity
import com.example.android.signin.signinActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 첫화면 -> bbs 로 이동
        // 지워도 되는 버튼(임시용)
        val bbsMove = findViewById<Button>(R.id.btnAuth)
        val login = findViewById<Button>(R.id.btnAuth2)
        val first = findViewById<Button>(R.id.btnAuth3)
        bbsMove.setOnClickListener {
            val i = Intent(this, Bbs::class.java)
            startActivity(i)
        }
        login.setOnClickListener {
            val i = Intent(this, signinActivity::class.java)
            startActivity(i)
        }
        first.setOnClickListener {
            val i = Intent(this,firstscreenActivity::class.java)
            startActivity(i)
        }
    }
}