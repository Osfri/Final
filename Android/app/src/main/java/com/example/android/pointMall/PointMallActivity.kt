package com.example.android.pointMall

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.example.android.R
import com.example.android.alram.AlramActivity
import com.example.android.bbs.BbsActivity
import com.example.android.bbs.BbsDto
import com.example.android.bbs.CustomAdapterBbsList
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.chat.ChatSingleton
import com.example.android.chat.ChatUserDto
import com.example.android.offday.OffDayActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto

class PointMallActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_mall)

        // TODO: 사이드바에서 쇼핑몰 진입시 오류 (로그인 객체 null)
        // (수정,추가_백엔드) 임시데이터 생성 (객체로 넘어올 경우 삭제)
        val loginUserId:String = intent.getStringExtra("loginUserId")!!
        val userInfo:ChatUserDto = ChatSingleton.getInstance().getLoginUserInfo(loginUserId)
        MemberDao.user = MemberDto(userInfo.id,userInfo.name,userInfo.email, userInfo.pw, userInfo.phonenumber,userInfo.code,userInfo.auth,userInfo.alarm, userInfo.alarmtime,userInfo.point)


        // (수정,추가_백엔드) 내용 최신화
        updateViewContent()


        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)
        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_PointMall)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }

    override fun onResume() {
        super.onResume()
        // (수정,추가_백엔드) 현재 페이지에서 point 최신화 필요 => 아래방식으로 사용가능 or 최신화 함수 추가필요
        val loginUserId:String = intent.getStringExtra("loginUserId")!!
        val userInfo:ChatUserDto = ChatSingleton.getInstance().getLoginUserInfo(loginUserId)
        MemberDao.user = MemberDto(userInfo.id,userInfo.name,userInfo.email, userInfo.pw, userInfo.phonenumber,userInfo.code,userInfo.auth,userInfo.alarm, userInfo.alarmtime,userInfo.point)

        // (수정,추가_백엔드) 내용 최신화
        updateViewContent()

    }

    // (수정,추가_백엔드) 최신화 항목
    fun updateViewContent(){
        // (수정,추가_백엔드) 로그인 유저 포인트 표시
        val userPoint:TextView = findViewById<TextView>(R.id.point_tv_point)
        userPoint.text = "나의 포인트: ${MemberDao.user!!.point}"

        // (수정,추가_백엔드) 상품목록 생성
        PointMallSingleton.getInstance().getShopItemAll()
        val itemList:MutableList<ShopDto> = PointMallSingleton.getInstance().shopItemList

        // (수정,추가_백엔드) 리사이클러뷰 생성
        var pointRecyclerView = findViewById<RecyclerView>(R.id.pointRecyclerView)  // bbsRecyclerView 변수

        val mAdapter = CustomAdapterPointMall(this, itemList)
        pointRecyclerView.adapter = mAdapter
        val layout = LinearLayoutManager(this)
        pointRecyclerView.layoutManager = layout
        pointRecyclerView.setHasFixedSize(true)

    }

    // drawerlayout bar 함수
    // 툴바 메뉴 버튼이 클릭 됐을 때 실행하는 함수
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
    // drawerlayout bar
    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
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