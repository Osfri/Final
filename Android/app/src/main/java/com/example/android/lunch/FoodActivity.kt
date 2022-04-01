package com.example.android.lunch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.chat.ChatSingleton
import com.example.android.chat.ChatUserDto
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.google.android.material.navigation.NavigationView

class FoodActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food)


        // TODO: 사이드바에서 쇼핑몰 진입시 오류 (로그인 객체 null)
        // (수정,추가_백엔드) 임시데이터 생성 (객체로 넘어올 경우 삭제)
        val loginUserId:String = intent.getStringExtra("loginUserId")!!
        val userInfo: ChatUserDto = ChatSingleton.getInstance().getLoginUserInfo(loginUserId)
        MemberDao.user = MemberDto(userInfo.id,userInfo.name,userInfo.email, userInfo.pw, userInfo.phonenumber,userInfo.code,userInfo.auth,userInfo.alarm, userInfo.alarmtime,userInfo.point)

        // TODO: 식단등록은 웹에서 등록하므로 등록기능 해제함
        // (수정,추가_백엔드) 관리자인 경우만 식단등록 표시 => 앱에서는 권한 관계없이 등록페이지 안보이도록 수정함
        val foodWriteBtn:Button = findViewById<Button>(R.id.btnFoodWrite)
        foodWriteBtn.visibility = View.GONE
        /*
        if(MemberDao.user!!.auth == 0 || MemberDao.user!!.auth == 3){   // 0: 관리자, 3: 최고관리자
            foodWriteBtn.visibility = View.VISIBLE
        }else{
            foodWriteBtn.visibility = View.GONE
        }
         */

        // (수정,추가_백엔드) 식단 데이터 생성
        FoodSingleton.getInstance().getSameCodeFoodInfo(MemberDao.user!!)
        val foodItemList: MutableList<FoodDto> = FoodSingleton.getInstance().foodItemList

        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Food)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너



        // 리사이클러 뷰
        var foodRecyclerView = findViewById<RecyclerView>(R.id.foodRecyclerView)
        val mAdapter = CustomAdapterFood(this, foodItemList)
        foodRecyclerView.adapter = mAdapter
        var layout = LinearLayoutManager(this)
        foodRecyclerView.layoutManager = layout
        foodRecyclerView.setHasFixedSize(true)



        // food -> foodwrite 이동    (식단 등록으로 가는 버튼)
        val btnFoodWrite = findViewById<Button>(R.id.btnFoodWrite)
        btnFoodWrite.setOnClickListener {
            val i = Intent(this, FoodWriteActivity::class.java)
            startActivity(i)
        }

    }

    // (수정,추가_백엔드)
    override fun onResume() {
        super.onResume()
        FoodSingleton.getInstance().getSameCodeFoodInfo(MemberDao.user!!)
        val foodItemList: MutableList<FoodDto> = FoodSingleton.getInstance().foodItemList
        val mAdapter = CustomAdapterFood(this, foodItemList)
        //mAdapter.notifyDataSetChanged()

        var foodRecyclerView = findViewById<RecyclerView>(R.id.foodRecyclerView)
        foodRecyclerView.adapter = mAdapter
        var layout = LinearLayoutManager(this)
        foodRecyclerView.layoutManager = layout
        foodRecyclerView.setHasFixedSize(true)
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
        }
        return false
    }



}