package com.example.android.bbs

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.manager.BoardtypeDto
import com.example.android.manager.ManagerActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView

class BbsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    // 게시판추가 리사이클러뷰 임시 확인 데이터(지워야됩니다)
    val typebbs = arrayListOf<BoardtypeDto>(
        BoardtypeDto(0,"게시판11111","814",3),
        BoardtypeDto(0,"게시판22222","1848",3)
        )

    //최초 보이는 게시판 공지사항0 건의사항1 게시판 클릭시 값 변경 해야함
    companion object {
        var type = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs)



//커밋 22 03 31 22 18
        // bbs리스트
        // 병원 코드 변환
        var code = MemberDao.user?.code!!
        if (code.contains("_")) {
            val split: List<String> = code.split("_")
            code = split[0]
        }
        // 게시물 가져오는 곳
        val userList:ArrayList<BbsDto> = BbsDao.getInstance().getBbsList(code, type)

        var bbslistRecyclerView = findViewById<RecyclerView>(R.id.bbsRecyclerView)  // bbsRecyclerView 변수
        val mAdapter = CustomAdapterBbsList(this, userList)
        bbslistRecyclerView.adapter = mAdapter
        val layout = LinearLayoutManager(this)
        bbslistRecyclerView.layoutManager = layout
        bbslistRecyclerView.setHasFixedSize(true)



        // 게시판 추가 부분 리사이클러뷰
        var bbsTypeRecyclerView = findViewById<RecyclerView>(R.id.bbsTypeRecyclerView)  // bbsRecyclerView 변수
        val mAdaptertype = CustomAdapterBbsType(this, typebbs)
        bbsTypeRecyclerView.adapter = mAdaptertype
        val layouttype = LinearLayoutManager(this)
        bbsTypeRecyclerView.layoutManager = layouttype
        bbsTypeRecyclerView.setHasFixedSize(true)



        // bbs -> bbsWrite 이동    (글쓰기로 가는 버튼)
        val btn_bbsListWrite = findViewById<Button>(R.id.btn_bbsListWrite)
        btn_bbsListWrite.setOnClickListener {
            val i = Intent(this, BbsWrite::class.java)
            startActivity(i)
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
        navigationView = findViewById(R.id.nav_Bbs)
        navigationView.setNavigationItemSelectedListener(this) //naviga

/*
        // 네비 메뉴 추가
        navigationView.menu.add(R.id.notice,0,0,"건의사항")
        navigationView.menu.get(1).setIcon(R.drawable.alarm_back_ring)


        val sel = findViewById<Button>(R.id.btn_bbsListSelect)
        sel.setOnClickListener {
            val se = findViewById<EditText>(R.id.se)
            navigationView.menu.add(R.id.notice,0,0,se.text.toString())
            navigationView.menu.get(2).setIcon(R.drawable.alarm_back_ring)
        }
*/


        /*
        새로고침 해야되는지 체크해야됨
        navigationView.menu.add(R.id.notice,999,2,"공지리리")
        navigationView.menu[navigationView.menu.size-1].setIcon(R.drawable.button_round_original)
        Log.d("로그","${navigationView.menu.size()}")
        */



    }

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
        menu!!.get(2).title = "게시판외"
        return super.onCreateOptionsMenu(menu)
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


            R.id.menu_bbs_important-> {                                                  // 공지사항
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