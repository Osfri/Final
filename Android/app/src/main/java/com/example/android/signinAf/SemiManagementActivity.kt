package com.example.android.signinAf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import kotlin.random.Random

class SemiManagementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semi_management)

        val semiMana_Btn_Finish = findViewById<Button>(R.id.semiMana_Btn_Finish)      // 관리자 등록 버튼
        val name = findViewById<EditText>(R.id.semiMana_Edit_HospitalName)
        val addr = findViewById<EditText>(R.id.semiMana_Edit_HospitalAddress)

        var index = true
        var randomPass:String = ""

        // 관리자 등록 후 메인페이지 이동
        semiMana_Btn_Finish.setOnClickListener {
            if(name.text.toString() == "" || addr.text.toString() == "" || name == null || addr == null){
                Toast.makeText(this,"병원명 또는 주소를 입력해 주세요",Toast.LENGTH_SHORT).show()
            }else{
                while (index){
                    val random = (1..999999).random().toString()
                    randomPass = random
                    println("코드===="+randomPass)
                    val check:HospitalDto? = MemberDao.getInstance().codeCheck(randomPass)
                    if(check == null){
                        println("중복 없음")
                        index = false
                        }
                    }
                val hospital = HospitalDto(name.text.toString(),addr.text.toString(),randomPass)
                println("병원생성===="+hospital.toString())
                val dto:Int? = MemberDao.getInstance().insertHospital(hospital)
                if (dto == 1){
                    Toast.makeText(this,"생성이 완료 되었습니다.",Toast.LENGTH_SHORT).show()
                    val mem = MemberDto(MemberDao.user?.id,MemberDao.user?.name,MemberDao.user?.email,MemberDao.user?.pw,MemberDao.user?.phonenumber,randomPass,3,MemberDao.user?.alarm,MemberDao.user?.alarmtime,MemberDao.user?.point)
                    MemberDao.getInstance().insertHospitalAf(mem)
                    MemberDao.user = mem
                    println("코드,권한 확인===="+MemberDao.user.toString())
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    Toast.makeText(this,"생성이 완료되지 않았습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}