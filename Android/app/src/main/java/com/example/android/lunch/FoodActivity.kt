package com.example.android.lunch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.alram.AlramActivity
import com.example.android.bbs.BbsActivity
import com.example.android.bbs.BbsWrite
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView

class FoodActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    // 데이터 확인용 변수로 지워도 됩니다
    var userList = arrayListOf<FoodDto>(
        FoodDto("1", "2022-06-08", "자장면,짬뽕"),
        FoodDto("2", "2022-05-08", "자장면"),
        FoodDto("3", "2022-01-08", "밥")


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food)



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
        val mAdapter = CustomAdapterFood(this, userList)
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
                val i = Intent(this, AlramActivity::class.java)
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