package com.example.android.firstscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android.R
import com.example.android.signin.SigninActivity

class firstscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)

        val signin = findViewById<Button>(R.id.btn_signin1)
        val signup = findViewById<Button>(R.id.btn_signin1)

        signin.setOnClickListener {
            val i = Intent(this,SigninActivity::class.java)
            startActivity(i)
        }


    }
}