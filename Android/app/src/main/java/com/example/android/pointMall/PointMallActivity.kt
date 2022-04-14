package com.example.android.pointMall

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.MainActivity
import com.google.android.material.navigation.NavigationView
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.chat.ChatSingleton
import com.example.android.chat.ChatUserDto
import com.example.android.lunch.FoodActivity
import com.example.android.manager.ManagerActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity

class PointMallActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_mall)

        // (수정,추가_백엔드) 로그인한 회원정보 생성
        val loginUserId:String = MemberDao.user!!.id!!
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

        // 페이지별 제목 표시 (가운데 정렬) 네비게이션 앱바
        val tv = findViewById<TextView>(R.id.navi_title_center)
        tv.setText("포인트몰")
    }

    override fun onResume() {
        super.onResume()
        // (수정,추가_백엔드) 로그인한 회원정보 생성
        val loginUserId:String = MemberDao.user!!.id!!
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
        PointMallSingleton.getInstance().getShopItemAll(MemberDao.user!!.code!!)
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
            // (수정,추가_백엔드) 연락처 이동 부분 추가
            R.id.menu_phonenumber -> {
                val i = Intent(this, PhoneNumActivity::class.java)
                startActivity(i)
            }
            R.id.menu_manager->  {
                val i = Intent(this, ManagerMenuActivity::class.java)
                startActivity(i)
            }
            R.id.menu_food-> {
                val i = Intent(this, FoodActivity::class.java)
                startActivity(i)
            }
            R.id.menu_logout-> {
                AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                        val i  = Intent(this, SigninActivity::class.java)
                        val dto = MemberDto("", "", "","","","",0,0,0,0)
                        MemberDao.user = dto
                        startActivity(i)
                    })
                    .setNegativeButton("아니요", null)
                    .create()
                    .show()
            }
        }
        return false
    }
}