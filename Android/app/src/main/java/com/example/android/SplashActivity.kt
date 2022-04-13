package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.UiThread
import com.bumptech.glide.Glide
import com.example.android.signin.SigninActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
/*    val logo_tv = findViewById<TextView>(R.id.logo_tv)
    val logo_ho = findViewById<ImageView>(R.id.logo_ho)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 로딩화면 움직이는 로고.gif
        val logogif= findViewById<ImageView>(R.id.gif_image)
        Glide.with(this).load(R.drawable.logogif).into(logogif)

        // 첫 로딩화면 페이지
       startLoading()

    }
    private fun startLoading(){
        val handler = Handler()
       // handler.postDelayed({ finish() }, 1000)
        handler.postDelayed({
            val intent = Intent(baseContext, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}