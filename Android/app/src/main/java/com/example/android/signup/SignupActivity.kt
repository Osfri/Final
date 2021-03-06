package com.example.android.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.R
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val email = findViewById<EditText>(R.id.signup_email)
        val name = findViewById<EditText>(R.id.signup_name)
        val pw = findViewById<EditText>(R.id.signup_pw)
        val phonenumber = findViewById<EditText>(R.id.signup_phonenumber)
        val btn = findViewById<Button>(R.id.btn_btup)
        val id = findViewById<EditText>(R.id.signup_id)

        val idCheck = findViewById<TextView>(R.id.id_checkup)
        val pwCheck = findViewById<TextView>(R.id.pwid_checkup)
        val nameCheck = findViewById<TextView>(R.id.nameid_checkup)
        val phonenumberCheck = findViewById<TextView>(R.id.phoneid_checkup)
        val emailCheck = findViewById<TextView>(R.id.emailid_checkup)

        val pwdTemp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}\$"
        val phoneTemp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})\$"


        var idCount = false
        var pwCount = false
        var nameCount = false
        var emailCount = false
        var phonenumberCount = false

        btn.isEnabled = false


        id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (id.text.toString() == "" || id.text == null) {
                    idCheck.text = "???????????? ????????? ?????????"
                    idCount = false
                    btn.isEnabled = false
                } else {
                    idCheck.text = ""
                    idCount = true
                    btn.isEnabled = idCount && pwCount && nameCount && emailCount && phonenumberCount
                }
            }

        })
        name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (name.text.toString() == "" || name.text == null) {
                    nameCheck.text = "????????? ????????? ?????????"
                    nameCount = false
                    btn.isEnabled = false
                } else {
                    nameCheck.text = ""
                    nameCount = true
                    btn.isEnabled = idCount && pwCount && nameCount && emailCount && phonenumberCount
                }
            }

        })
        pw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!Pattern.matches(pwdTemp, pw.text)) {
                    pwCheck.text = "???????????? ????????? ???????????? 6~12????????? ????????? ?????????."
                    pwCount = false
                    btn.isEnabled = false
                } else {
                    pwCheck.text = ""
                    pwCount = true
                    btn.isEnabled = idCount && pwCount && nameCount && emailCount && phonenumberCount
                }
            }

        })
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
                    emailCheck.text = "????????? ????????? ?????? ?????????."
                    emailCheck.gravity = Gravity.LEFT
                    emailCount = false
                    btn.isEnabled = false
                } else {
                    emailCheck.text = ""
                    emailCount = true
                    btn.isEnabled = idCount && pwCount && nameCount && emailCount && phonenumberCount
                }
            }

        })
        phonenumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!Pattern.matches(phoneTemp, phonenumber.text)) {
                    phonenumberCheck.text = "????????? ????????? ?????? ?????? ?????????."
                    phonenumberCount = false
                    btn.isEnabled = false
                } else {
                    phonenumberCheck.text = ""
                    phonenumberCount = true
                    btn.isEnabled = idCount && pwCount && nameCount && emailCount && phonenumberCount
                }
            }

        })
        btn.setOnClickListener {
            var idindex = false
            var emailindex = false
            val idchecking = MemberDto(id.text.toString(), null, null, null, null, null, 0, 0, 0, 0)
            val idmem = MemberDao.getInstance().idCheck(idchecking)
            if (idmem == "" || idmem == null) {
                idCheck.text = ""
                idindex = true
            } else {
                idCheck.text = "???????????? ????????? ?????????"
            }

            val emailchecking = MemberDto(null, null, email.text.toString(), null, null, null, 0, 0, 0, 0)
            val emailmem = MemberDao.getInstance().emailCheck(emailchecking)
            if (emailmem == "" || emailmem == null){
                emailCheck.text = ""
                emailindex = true
            }else{
                emailCheck.text = "?????? ????????? ????????? ?????????."
            }

            if (idindex && emailindex){
                val dto = MemberDto(
                    id.text.toString(), name.text.toString(), email.text.toString(),
                    pw.text.toString(), phonenumber.text.toString(), "a", 5, 0, null, 0
                )
                val mem = MemberDao.getInstance().register(dto)
                println("------------------------------" + mem)
                    Toast.makeText(this, "??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, SigninActivity::class.java)
                    startActivity(i)
            }
        }
    }
}
