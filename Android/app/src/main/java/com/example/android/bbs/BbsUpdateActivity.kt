package com.example.android.bbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.manager.BoardtypeDto
import com.example.android.manager.ManagerActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView

class BbsUpdateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_update)

        val data = intent.getParcelableExtra<BbsDto>("data")
        val bbstype = intent?.getParcelableExtra<BoardtypeDto>("dataType")

        val updateBtn = findViewById<Button>(R.id.btn_bbsUpdateFin)
        val updateTitle = findViewById<EditText>(R.id.bbsUpdateTitle)
        val updateContent = findViewById<EditText>(R.id.bbsUpdateContent)
        val updateText = findViewById<TextView>(R.id.bbsUpdateId)
        val updateImage = findViewById<ImageView>(R.id.bbsUpdateImage)

        if (data?.image == "" || data?.image == null || data?.image == " "){
            updateImage.isGone
        }else{
            updateImage.setImageURI(data?.image?.toUri())
        }

        var title = ""
        var content = ""
        val typename = if (BbsActivity.typename == ""){"공지사항"}else{BbsActivity.typename}
        updateText.text = "작성자: ${MemberDao.user!!.id} 게시판:${typename}"

        updateTitle.setText("${data?.title}")
        updateContent.setText("${data?.content}")

        updateTitle.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                title = updateTitle.text.toString()
            }
        })
        updateContent.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                content = updateContent.text.toString()
            }
        })

        updateBtn.setOnClickListener {
            val dto = BbsDto(data!!.seq,MemberDao.user?.id,title,content,0,data.wdate,0,data.type,data.code,0,data.gr,"")
            BbsDao.getInstance().updateBbs(dto)
            startActivity(Intent(this,BbsActivity::class.java))
        }





        //=============================================Front===========================================================
        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Bbs_Update)
        navigationView.setNavigationItemSelectedListener(this) //naviga









    }



    // 네비게이션바
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
        when(item!!.itemId){
            android.R.id.home->{
                // 햄버거 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }
    // 네비게이션바
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){


            R.id.menu_bbs-> {                                                  // 공지사항
                val i = Intent(this, BbsActivity::class.java)
                Log.d("로그","공지")
                startActivity(i)

            }
            R.id.menu_alram-> {
                val i = Intent(this, AlarmActivity::class.java)
                startActivity(i)
            }
            R.id.menu_cal->  {
                val i = Intent(this, CalendarActivity::class.java)
                startActivity(i)
            }
            R.id.menu_point->  {
                val i = Intent(this, PointMallActivity::class.java)
                startActivity(i)
            }
            R.id.menu_chat->  {
                val i = Intent(this, ChatActivity::class.java)
                startActivity(i)
            }
            R.id.menu_offday->  {
                val i = Intent(this, OffDayActivity::class.java)
                startActivity(i)
            }
            R.id.menu_phonenumber -> {
                val i = Intent(this, PhoneNumActivity::class.java)
                startActivity(i)
            }
            R.id.menu_manager->  {
                val i = Intent(this, ManagerMenuActivity::class.java)
                startActivity(i)
            }

        }
        return false
    }
}