package com.example.android.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.android.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val email = findViewById<EditText>(R.id.signup_email)
        val name = findViewById<EditText>(R.id.signup_name)
        val pw = findViewById<EditText>(R.id.signup_pw)
        val phonenumber = findViewById<EditText>(R.id.signup_phonenumber)
        val btn = findViewById<Button>(R.id.btn_btup)



        btn.setOnClickListener {

        }
    }
}