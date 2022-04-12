package com.example.android.manager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.manager.bbs.ManagerBbsActivity
import com.example.android.manager.staff.ManagerStaffActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView

class ManagerMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    override fun onBackPressed() {
        val main = Intent(this, MainActivity::class.java)
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(main)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_menu)
















        val manage_Btn_Bbs = findViewById<ImageButton>(R.id.manage_Btn_Bbs)
        val manage_Btn_Staff = findViewById<ImageButton>(R.id.manage_Btn_Staff)
        // 게시물 관리
        manage_Btn_Bbs.setOnClickListener {
            val i = Intent(this, ManagerBbsActivity::class.java)
            startActivity(i)
        }

        // 게시물 관리
        manage_Btn_Staff.setOnClickListener {
            val i = Intent(this, ManagerStaffActivity::class.java)
            startActivity(i)
        }

        // drawerlayout bar 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Manager_menu)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

    }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
            when (item!!.itemId) {
                android.R.id.home -> {
                    // 햄버거 버튼 클릭시 네비게이션 드로어 열기
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
            return super.onOptionsItemSelected(item)
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_bbs -> {
                    val i = Intent(this, BbsActivity::class.java)
                    startActivity(i)
                }
                R.id.menu_alram -> {
                    val i = Intent(this, AlarmActivity::class.java)
                    startActivity(i)
                }
                R.id.menu_cal -> {
                    val i = Intent(this, CalendarActivity::class.java)
                    startActivity(i)
                }
                R.id.menu_point -> {
                    val i = Intent(this, PointMallActivity::class.java)
                    startActivity(i)
                }
                R.id.menu_chat -> {
                    val i = Intent(this, ChatActivity::class.java)
                    startActivity(i)
                }
                R.id.menu_phonenumber -> {
                    val i = Intent(this, PhoneNumActivity::class.java)
                    startActivity(i)
                }
                R.id.menu_offday -> {
                    val i = Intent(this, OffDayActivity::class.java)
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
