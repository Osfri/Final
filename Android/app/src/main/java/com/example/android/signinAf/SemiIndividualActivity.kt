package com.example.android.signinAf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.android.R
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto

class SemiIndividualActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semi_individual)


        val semiInviBtnHospitalCode = findViewById<Button>(R.id.semiInvi_Btn_HospitalCode)      // 개인 코드등록 버튼
        val editText = findViewById<EditText>(R.id.semiInvi_Edit_HospitalCode)
        val text = findViewById<TextView>(R.id.semiInvi_Text_HospitalCode)
        text.text = ""

        semiInviBtnHospitalCode.isEnabled = false

        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val dto = MemberDao.getInstance().codeCheck(editText.text.toString())
                if (dto == null){
                    text.text = "코드를 알맞게 입력해 주세요"
                }else{
                    text.text = dto.code
                    semiInviBtnHospitalCode.isEnabled = true
                }
            }


        })
        // 코드등록 후 승인대기 페이지로 이동
        semiInviBtnHospitalCode.setOnClickListener {
            val dto = MemberDto(MemberDao.user?.id.toString(),MemberDao.user?.name,MemberDao.user?.email,MemberDao.user?.pw,MemberDao.user?.phonenumber,text.text.toString(),2,MemberDao.user?.alarm,MemberDao.user?.alarmtime,MemberDao.user?.point)
            MemberDao.getInstance().insertHospitalAf(dto)
            startActivity(Intent(this,SemiWaitingActivity::class.java))
        }
    }
}