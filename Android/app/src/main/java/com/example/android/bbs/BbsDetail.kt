package com.example.android.bbs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.view_item_layout_comment.view.*
import java.time.LocalDate

class BbsDetail : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    override fun onBackPressed() {
        val i = Intent(this,BbsActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    companion object {
        var BbsDetail: BbsDetail? = null

        fun getInstance(): BbsDetail {
            if (BbsDetail == null) {
                BbsDetail = BbsDetail()
            }
            return BbsDetail!!
        }

        var Detaildata:BbsDto? = null
        var test:RecyclerView? = null
    }


    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_detail)



        val data = intent.getParcelableExtra<BbsDto>("data")
        if (data != null){
            MemberDao.BBsDetaildata = data
        }
        Detaildata = MemberDao.BBsDetaildata
        // 디테일 글 정보 표시

        var bbsDetailCommentRecycleview = findViewById<RecyclerView>(R.id.bbsDetailCommentRecycleview)  // bbsRecyclerView 변수
        test = bbsDetailCommentRecycleview
        val bbsDetailId = findViewById<TextView>(R.id.bbsDetailId)
        val bbsDetailCount = findViewById<TextView>(R.id.bbsDetailCount)
        val bbsDetailTitle = findViewById<TextView>(R.id.bbsDetailTitle)
        val bbsDetailImage = findViewById<ImageView>(R.id.bbsDetailImage)
        val bbsDetailContent = findViewById<TextView>(R.id.bbsDetailContent)
        val bbsDetailDate = findViewById<TextView>(R.id.bbsDetailDate)
        bbsDetailId.text = Detaildata?.id
        bbsDetailCount.text = Detaildata?.readCount.toString()
        bbsDetailTitle.text = Detaildata?.title
        bbsDetailContent.text = Detaildata?.content
        bbsDetailDate.text = Detaildata?.wdate
        if (Detaildata?.image == "" || Detaildata?.image == null || Detaildata?.image == " "){
            //bbsDetailImage.isGone
        }else{
            Glide.with(this)
                .load(Detaildata?.image) // 불러올 이미지 url
                .into(bbsDetailImage) // 이미지를 넣을 뷰
            //bbsDetailImage.setImageURI(Detaildata?.image?.toUri())
        }


        // 디테일 하단부 버튼들  (댓글쓰기, 글수정, 글삭제)
        val btnDetailDel = findViewById<TextView>(R.id.btnDetailDel)                    // 글 삭제
        val btnDetailUpdate = findViewById<TextView>(R.id.btnDetailUpdate)              // 글 수정
        val bbsCommentEditText = findViewById<EditText>(R.id.bbsCommentEditText)        // 댓글 입력
        val btnCommentWrite = findViewById<Button>(R.id.btnCommentWrite)                // 댓글 쓰기 버튼

        //삭제 - 본인,관리자 수정 - 본인
        if (MemberDao.user?.id.toString() != Detaildata?.id.toString()){
            btnDetailUpdate.isEnabled = false
            if (MemberDao.user?.id.toString() != Detaildata?.id.toString() || MemberDao.user?.auth != 0){
                btnDetailDel.isEnabled = false
            }
        }

        val bbsCommentRecycleViewDelete = findViewById<Button>(R.id.bbsCommentRecycleViewDelete)    // 리사이클러뷰 댓글 삭제 버튼
        val bbsCommentRecycleViewUpdate = findViewById<Button>(R.id.bbsCommentRecycleViewUpdate)    // 리사이클러뷰 댓글 수정 버튼
        val bbsCommentRecycleViewWriter = findViewById<TextView>(R.id.bbsCommentRecycleViewWriter)  // 리사이클러뷰 댓글 작성자 표시 텍스트


        btnCommentWrite.setOnClickListener {
            val onlyDate: String = LocalDate.now().toString()
            val dto = BbsDto(0,MemberDao.user!!.id.toString(),"댓글",bbsCommentEditText.text.toString(),0,onlyDate,0,Detaildata!!.type,
                Detaildata!!.code,1,
                Detaildata!!.gr,"")
            BbsDao.getInstance().commentwrite(dto)
            commentlist()
        }

        btnDetailDel.setOnClickListener {
            BbsDao.getInstance().deleteBbs(Detaildata!!.seq)
            val i = Intent(this, BbsActivity::class.java)
            startActivity(i)
        }

        btnDetailUpdate.setOnClickListener {
            val i = Intent(this,BbsUpdateActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            i.putExtra("updatedata", Detaildata)
            startActivity(i)
        }

        // 댓글리스트

        val mAdapter = CustomAdapterCommentList(this, commentlist())
        test?.adapter = mAdapter
        val layout = LinearLayoutManager(this)
        test?.layoutManager = layout
        test?.setHasFixedSize(true)
        //commentlist(Detaildata!!)



        // bbsDetail -> Bbs 로 이동
/*        val bbsDetailBack = findViewById<Button>(R.id.bbsDetailBack)
        bbsDetailBack.setOnClickListener {
            val i = Intent(this, BbsActivity::class.java)
            startActivity(i)
        }*/



        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Bbs_Detail)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너



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

    @SuppressLint("NotifyDataSetChanged")
    fun commentlist(): ArrayList<BbsDto>{
        val commentlist:ArrayList<BbsDto>? = BbsDao.getInstance().getCommentList(Detaildata!!.gr)
        CustomAdapterCommentList(this,commentlist!!).notifyDataSetChanged()
        return commentlist
    }






}