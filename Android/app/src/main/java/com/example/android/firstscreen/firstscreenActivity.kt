package com.example.android.firstscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.R
import com.example.android.bbs.Bbs
import com.example.android.signin.signinActivity

class firstscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)

        val signin = findViewById<Button>(R.id.btn_signin)
        val signup = findViewById<Button>(R.id.btn_signin)

        signin.setOnClickListener {
            val i = Intent(this,signinActivity::class.java)
            startActivity(i)
        }


    }
}