package com.example.android.manager.bbs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.bbs.BbsDao
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.manager.BoardtypeDto
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView


class ManagerBbsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


        var userList = BbsDao.getInstance().getBoardTypeList(MemberDao.user?.code!!)

    override fun onBackPressed() {
        val main = Intent(this, ManagerMenuActivity::class.java)
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_bbs)

        var boardAuth = 0  // 관리자만 작성가능 0 , 모두 작성가능 1
        val manager_btn_check = findViewById<Button>(R.id.manager_btn_check)                // 게시판 등록 버튼
        val radioGroups = findViewById<RadioGroup>(R.id.manager_bbs_radioGroup)             // 라디오 버튼 그룹
        val manager_et_bbs = findViewById<EditText>(R.id.manager_et_bbs)                    // 게시판명 입력창
        val manage_btn_bbsdelete =  findViewById<Button>(R.id.manage_btn_bbsdelete)         // 리사이클러뷰 삭제버튼




        // 라디오버튼 선택    관리자/개인
        radioGroups.setOnCheckedChangeListener { _, checkedId ->    // checkedId는 어디서 나왔냐, 임의로 생성한 말
            Log.d("RadioButton", "라디오 버튼 활성화")
            when(checkedId) {
                R.id.manager_bbs_radioManager -> {
                    Toast.makeText(this,"관리자 권한으로 게시판이 생성됩니다", Toast.LENGTH_SHORT).show()
                    boardAuth = 0
                }     // 텍스트 뷰에다가 문자를 넣는 (버튼 클릭시 텍스트 뷰에 글이 뜬다)
                R.id.manager_bbs_radioStaff -> {
                    Toast.makeText(this,"개인 권한으로 게시판이 생성됩니다", Toast.LENGTH_SHORT).show()
                    boardAuth = 1
                }

            }
        }
        // 게시판 등록
        var result = true
        var type = 0
        manager_btn_check.setOnClickListener {
            while (result){
            var random:Int = (1..999999).random()
            val temp:BoardtypeDto? = BbsDao.getInstance().bbsRandomCheck(random)
                if (temp == null){
                    type = random
                    println("===randon"+random)
                    println("===randon"+type)
                    result = false
                }
            }
            var code = MemberDao.user!!.code
            if (code!!.contains("_")){
                code = code.split("_")[0]
            }
            println("===randon"+type)
            val typeDto = BoardtypeDto(type,manager_et_bbs.text.toString(),code,boardAuth)
            BbsDao.getInstance().bbsAdd(typeDto)
            println("===========================fdsafdsafdsa"+typeDto.toString())
        }














        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Manager_bbs)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

        // 페이지별 제목 표시 (가운데 정렬) 네비게이션 앱바
        val tv = findViewById<TextView>(R.id.navi_title_center)
        tv.setText("관리자페이지")


        // 리사이클러 뷰
        var managerRecyclerView = findViewById<RecyclerView>(R.id.managerRecyclerView)
        val mAdapter = CustomAdapterManagerBbs(this, userList!!)
        managerRecyclerView.adapter = mAdapter
        var layout = LinearLayoutManager(this)
        managerRecyclerView.layoutManager = layout
        managerRecyclerView.setHasFixedSize(true)


    }

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
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_bbs-> {
                val i = Intent(this, BbsActivity::class.java)
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