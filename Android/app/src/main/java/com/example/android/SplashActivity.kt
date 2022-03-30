package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 첫 로딩화면 페이지
        startLoading()
    }
    private fun startLoading(){
        val handler = Handler()
        handler.postDelayed({ finish() }, 1000)
    }
}