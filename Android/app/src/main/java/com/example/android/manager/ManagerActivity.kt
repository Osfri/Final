package com.example.android.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.bbs.BbsDao
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView
import kotlin.random.Random


class ManagerActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

/*  게시물 관리로 가야될 변수

        // 게시판 추가manage_btn_bbs
       val managebtnBbs = findViewById<Button>(R.id.manage_btn_bbs)
        val edit = findViewById<EditText>(R.id.manage_et_bbs)
        var index = true
        var randomPass = 0

        //글쓰기 권한( 관리자만 0,모두 1)
        var auth = 0

        managebtnBbs.setOnClickListener {
            while (index){
                val random = (1..999999).random()
                randomPass = random
                val result:BoardtypeDto = BbsDao.getInstance().bbsRandomCheck(randomPass)
                if (result == null){
                    index = false
                }
            }
            val split = MemberDao.user!!.code!!.split("_")[0]
            val BoardtypeDto = BoardtypeDto(randomPass,edit.text.toString(),split,auth)
            BbsDao.getInstance().bbsAdd(BoardtypeDto)
            /*val i = Intent(this, BbsActivity::class.java)
            startActivity(i)*/
        }

*/


















        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Manager)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너



        // 네비 메뉴 추가
        navigationView.menu.add(R.id.notice,0,0,"건의사항")
        navigationView.menu.get(1).setIcon(R.drawable.alarm_back_ring)

        // 게시판 생성 입력창과 버튼
        val manage_btn_bbs = findViewById<Button>(R.id.manage_btn_bbs)
        manage_btn_bbs.setOnClickListener {
            val manage_et_bbs = findViewById<EditText>(R.id.manage_et_bbs)
            navigationView.menu.add(R.id.notice,0,0,manage_et_bbs.text.toString())
            navigationView.menu.get(2).setIcon(R.drawable.alarm_back_ring)
        }


        /*
        새로고침 해야되는지 체크해야됨
        navigationView.menu.add(R.id.notice,999,2,"공지리리")
        navigationView.menu[navigationView.menu.size-1].setIcon(R.drawable.button_round_original)
        Log.d("로그","${navigationView.menu.size()}")
        */



    }
/*
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d("로그","${menu!![0].title}")
        return super.onPrepareOptionsMenu(menu)
    }

    // 오른쪽 메뉴 ... 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.ham_menu_bbslist,menu)
        //val menuitem =  menu!!.findItem(R.id.menu_bbs_important)
        Log.d("로그","@@@${menu!!.get(0).title}")


        val mi:MenuItem = menu!!.add(0,100,2,"sub")
        mi.setIcon(R.drawable.ic_bbs)
        menu!!.get(2).title = "이룬"
        return super.onCreateOptionsMenu(menu)
    }*/


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


            R.id.menu_bbs-> {                                                  // 공지사항
                val i = Intent(this, BbsActivity::class.java)
                Log.d("로그","공지")
                startActivity(i)

            }
            R.id.menu_alram-> {

/*
                navigationView.menu.add(R.id.notice,0,0,"게시판221")
                navigationView.menu.get(3).setIcon(R.drawable.alarm_back_ring)
                navigationView.invalidateOutline()
                navigationView.invalidate()*/

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
            R.id.menu_manager->  {
                val i = Intent(this, ManagerActivity::class.java)
                startActivity(i)
            }

        }
        return false
    }
}