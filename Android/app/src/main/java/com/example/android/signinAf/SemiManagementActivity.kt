package com.example.android.signinAf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.bbs.BbsDao
import com.example.android.manager.BoardtypeDto
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
        semiMana_Btn_Finish.setOnClickListener { // 병원 생성 버튼
            if(name.text.toString() == "" || addr.text.toString() == "" || name == null || addr == null){
                //병원명,주소 미입력
                Toast.makeText(this,"병원명 또는 주소를 입력해 주세요",Toast.LENGTH_SHORT).show()
            }else{
                //병원명,주소 입력
                while (index){
                    //병원 코드 랜덤 생성
                    val random = (1..999999).random().toString()
                    randomPass = random
                    println("코드===="+randomPass)
                    //랜덤 코드가 DB에 이미 있을 경우 while 반복문 다시 실행
                    val check:HospitalDto? = MemberDao.getInstance().codeCheck(randomPass)
                    if(check == null){
                        //랜덤 코드가 DB에 없을 경우 index = false 를 통해 while 문 탈출
                        println("중복 없음")
                        index = false
                        }
                    }
                // DB에 병원 정보 insert
                val hospital = HospitalDto(name.text.toString(),addr.text.toString(),randomPass)
                println("병원생성===="+hospital.toString())
                val dto:Int? = MemberDao.getInstance().insertHospital(hospital)
                //생성 완료시
                if (dto == 1){
                    Toast.makeText(this,"생성이 완료 되었습니다.",Toast.LENGTH_SHORT).show()
                    //MemberDao 에 code를 포함한 로그인 정보 넣기
                    val mem = MemberDto(MemberDao.user?.id,MemberDao.user?.name,MemberDao.user?.email,MemberDao.user?.pw,MemberDao.user?.phonenumber,randomPass,3,MemberDao.user?.alarm,MemberDao.user?.alarmtime,MemberDao.user?.point)
                    MemberDao.getInstance().insertHospitalAf(mem)
                    MemberDao.user = mem
                    println("코드,권한 확인===="+MemberDao.user.toString())
                    //최초 공지사항,건희사항 게시판 생성
                    val split = MemberDao.user!!.code!!
                    BbsDao.getInstance().bbsAdd(BoardtypeDto(0,"공지사항",split,0))
                    BbsDao.getInstance().bbsAdd(BoardtypeDto(1,"건희사항",split,1))
                    //메인화면
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    //오류
                    Toast.makeText(this,"생성이 완료되지 않았습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}