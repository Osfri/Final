package com.example.android.bbs

import android.annotation.SuppressLint
import android.app.VoiceInteractor
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.lunch.FoodActivity
import com.example.android.manager.BoardtypeDto
import com.example.android.manager.ManagerActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import com.google.android.material.navigation.NavigationView

class BbsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    companion object {
        //최초 보이는 게시판 공지사항0 건의사항1 게시판 클릭시 값 변경
        var type = 0
        //게시물 쓰기 권한 관리자만0 모두1 게시판 클릭시 값 변경
        var bbswriteAuth = 0
        //게시물 쓰기 전용 가져갈 데이터 게시판 이름
        var typename = ""
    }


    override fun onBackPressed() {
        val main = Intent(this,MainActivity::class.java)
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(main)
    }
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs)



        // 병원 코드 변환
        var code = MemberDao.user?.code!!
        if (code.contains("_")) {
            val split: List<String> = code.split("_")
            code = split[0]
        }

        // 게시판 불러오기
        val typebbs = BbsDao.getInstance().getBoardTypeList(code)
        println("=========================================="+typebbs.toString())


        // 클릭한 게시판 타입 불러오기
        val a = intent?.getParcelableExtra<BoardtypeDto>("dataType")
        if (a !== null){
            type = a.type
            bbswriteAuth = a.auth
            typename = a.name!!
        }
        // 클릭한 게시판 게시물 불러오기
        val headmenu = findViewById<TextView>(R.id.navi_title_center)
        headmenu.text = if (typename == ""){"공지사항"}else{"${a!!.name}"}
        val userList = BbsDao.getInstance().getBbsList(code, type)



        // 게시판 리사이클러뷰
        var bbsTypeRecyclerView = findViewById<RecyclerView>(R.id.bbsTypeRecyclerView)
        val mAdaptertype = CustomAdapterBbsType(this, typebbs!!)
        bbsTypeRecyclerView.adapter = mAdaptertype
        bbsTypeRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bbsTypeRecyclerView.setHasFixedSize(true)

        // 게시물 리사이클러뷰
        try {
            var bbslistRecyclerView = findViewById<RecyclerView>(R.id.bbsRecyclerView)  // bbsRecyclerView 변수
            val mAdapter = CustomAdapterBbsList(this, userList!!)
            bbslistRecyclerView.adapter = mAdapter
            val layout = LinearLayoutManager(this)
            bbslistRecyclerView.layoutManager = layout
            bbslistRecyclerView.setHasFixedSize(true)
        }catch (e:NullPointerException){
            Toast.makeText(this,"작성된 게시글이 없습니다.",Toast.LENGTH_SHORT).show()
        }
/*
        검색창 일단 보류
        val bbs_et_select = findViewById<EditText>(R.id.bbs_et_select)
        val btn_bbsListSelect = findViewById<Button>(R.id.btn_bbsListSelect)
*/






        // 글쓰기
        val btn_bbsListWrite = findViewById<Button>(R.id.btn_bbsListWrite)
        // 글쓰기 권한 확인
        if (bbswriteAuth == 0){
            if (MemberDao.user!!.auth != 0){
                btn_bbsListWrite.visibility=View.INVISIBLE
            }
        }
        btn_bbsListWrite.setOnClickListener {
            val i = Intent(this, BbsWrite::class.java)
            startActivity(i)
        }


        //=============================================Front===========================================================
        // drawerlayout bar 설정

        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 페이지별 제목 표시 (가운데 정렬) 네비게이션 앱바


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
/*      오른쪽 상단 점 3개짜리 게시판 추가용도 인데 안씀
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
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val loginId = findViewById<TextView>(R.id.hamLoginId)
        val mid = MemberDao.user!!.name
        loginId.text =mid.toString()+" 님"
        val loginCode = findViewById<TextView>(R.id.hamLoginCode)
        loginCode.text = "환영합니다"
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
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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