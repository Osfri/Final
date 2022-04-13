package com.example.android.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.signinAf.SemiScreenActivity
import com.example.android.signinAf.SemiWaitingActivity
import com.example.android.signup.SignupActivity

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val mContext = this

        val id = findViewById<EditText>(R.id.signin_email)
        val pw = findViewById<EditText>(R.id.signin_pw)
        val login = findViewById<Button>(R.id.btn_btin)
        val checkbox = findViewById<CheckBox>(R.id.Signin_autoin)
        val signup = findViewById<TextView>(R.id.signin_move)

        /*val boo:Boolean = PreferenceManager.getBoolean(mContext,"check") //로그인 정보 기억하기 체크 유무 확인
        if(boo) { // 체크가 되어있다면 아래 코드를 수행 //저장된 아이디와 암호를 가져와 셋팅한다.
            id.setText(PreferenceManager.getString(mContext, "id"));
            pw.setText(PreferenceManager.getString(mContext, "pw"));
            checkbox.isChecked = true; //체크박스는 여전히 체크 표시 하도록 셋팅
            val dto = MemberDto(id.text.toString(), null, null, pw.text.toString(), null, null, 0, 0, 0, 0)
            val mem = MemberDao.getInstance().login(dto)
            MemberDao.user = mem
            when (MemberDao.user!!.auth) {
                null -> {} // 생성,추가 페이지
                2 -> {} // 승인대기 페이지
                0, 1 -> {} // main페이지
            }
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        }*/

        signup.setOnClickListener {
            val i = Intent(this, SignupActivity::class.java)
            startActivity(i)
        }
        login.setOnClickListener {
            val idtext: String = id.text.toString()
            val pwd: String = pw.text.toString()

            if (idtext == "" || pwd == "") {
                Toast.makeText(this, "내용을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                val dto = MemberDto(idtext, null, null, pwd, null, null, 0, 0, 0, 0)
                val mem = MemberDao.getInstance().login(dto)
                println(mem.toString())
                if (mem != null) {
                    MemberDao.user = mem
                    when (MemberDao.user!!.auth) {
                        5 -> {
                            startActivity(Intent(this,SemiScreenActivity::class.java))
                        } // 생성,추가 페이지
                        2 -> {
                            startActivity(Intent(this,SemiWaitingActivity::class.java))
                        } // 승인대기 페이지
                        0, 1, 3 -> {
                            startActivity(Intent(this,MainActivity::class.java))
                        } // main페이지
                    }
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    //아이디 패스워드 저장
                    if (checkbox.isChecked) {
                        println("fdsafdsafdsajgvfhfasdjvhfjsahfjlbvhasjl")
                        PreferenceManager.setString(mContext, "id", idtext); //id 키값으로 저장
                        PreferenceManager.setString(mContext, "pw", pwd); //pw 키값으로 저장
                        PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked); //현재 체크박스 상태 값 저장
                    }else{
                        PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked); //현재 체크박스 상태 값 저장
                        PreferenceManager.clear(mContext); //로그인 정보를 모두 날림
                    }
                } else {
                    Toast.makeText(this, "아이디 또는 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}