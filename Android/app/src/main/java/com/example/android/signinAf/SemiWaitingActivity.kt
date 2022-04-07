package com.example.android.signinAf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.android.R

class SemiWaitingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semi_waiting)


        // 로딩화면 움직이는 로고.gif
        val logogif= findViewById<ImageView>(R.id.semi_gif_waiting)
        Glide.with(this).load(R.drawable.waiting_gif).into(logogif)
    }
}