package com.example.android.pointMall

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.lunch.FoodActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import com.google.android.material.navigation.NavigationView


class PointMallDetailActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_mall_detail)

        // (수정,추가_백엔드) 로그인 유저 포인트 표시
        val userPoint:TextView = findViewById<TextView>(R.id.pointDetail_tv_Point)
        userPoint.text = "나의 포인트: ${MemberDao.user!!.point}"


        // (수정,추가_백엔드) 선택한 상품 seq
        val shopItemSeq: Int = intent.getIntExtra("selectShopItem",1 )
        val shopItemInfo: ShopDto = PointMallSingleton.getInstance().getShopItemInfo(ShopDto(shopItemSeq))
        shopItemInfoView(shopItemInfo, this)

/*

        // (수정,추가_백엔드) 관리자만 구매내역표시
        val historyBtn = findViewById<Button>(R.id.pointDetail_btn_History)
        if(MemberDao.user!!.auth !=0 && MemberDao.user!!.auth !=3){ // 0: 관리자, 3: 최고관리자
            historyBtn.visibility = View.GONE
        }
*/


        // (수정,추가_백엔드) 구매버튼 클릭시
        val buyBtn:Button = findViewById<Button>(R.id.pointDetail_btn_Buy)
        val dialogView:View = layoutInflater.inflate(R.layout.view_dialog_layout_pointmall, null)
        val cnt:TextView = dialogView.findViewById<TextView>(R.id.pointmall_dialog_cnt)
        val plusBtn:Button = dialogView.findViewById<Button>(R.id.pointmall_dialog_plusBtn)
        val minusBtn:Button = dialogView.findViewById<Button>(R.id.pointmall_dialog_minusBtn)

        buyBtn.setOnClickListener {
            var toast:Toast ?= null
            if(MemberDao.user!!.point!! >= shopItemInfo.price){
                // 구매수량 초기화
                cnt.text = "1"

                // + 버튼 클릭시
                plusBtn.setOnClickListener {
                    cnt.text = (cnt.text.toString().toInt() + 1).toString()
                    // 재고가 없을 경우
                    if(cnt.text.toString().toInt() > shopItemInfo.cnt){
                        cnt.text = shopItemInfo.cnt.toString()
                        if(toast != null) toast!!.cancel()
                        toast = Toast.makeText(this, "더 이상 구매할수 없습니다",Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
                }
                // - 버튼 클릭시
                minusBtn.setOnClickListener {
                    cnt.text = (cnt.text.toString().toInt() - 1).toString()
                    if(cnt.text.toString().toInt()<0){
                        cnt.text = "0"
                    }
                }

                // 이미 생성된 다이얼로그 초기화
                if(dialogView.parent != null) (dialogView.parent as ViewGroup).removeView(dialogView)

                // 결과 다이얼로그
                val builder = AlertDialog.Builder(this)
                builder.setView(dialogView)
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                        // 구매 수량이 1개 이상일 경우
                        if(cnt.text.toString().toInt()>0){
                            // 구매 가능할 경우
                            if(MemberDao.user!!.point!! >= (shopItemInfo.price * cnt.text.toString().toInt())){
                                // 해당 아이템 구매 (포인트 차감, 주문내역추가, 재고변경)
                                PointMallSingleton.getInstance().buyShopItemPoint(MemberDao.user!!, shopItemInfo, cnt.text.toString().toInt())
                                PointMallSingleton.getInstance().orderShopItem(MemberDao.user!!, shopItemInfo, cnt.text.toString().toInt())
                                PointMallSingleton.getInstance().buyShopItemCnt(shopItemInfo, cnt.text.toString().toInt())

                                if(toast != null) toast!!.cancel()
                                toast = Toast.makeText(this, "구매가 완료되었습니다!",Toast.LENGTH_SHORT)
                                toast!!.show()

                                finish()
                            }else{  // 구매 불가능할 경우
                                if(toast != null) toast!!.cancel()
                                toast = Toast.makeText(this, "포인트가 부족합니다",Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                        }
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                    })
                    .setCancelable(false)
                    .show()


            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("잔액부족")
                    .setMessage("현재잔액: ${MemberDao.user!!.point!!}")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                builder.show()
            }
        }




        // 내용부분 스크롤뷰 적용
        val m_TextViewLog = findViewById<TextView>(R.id.pointDetail_tv_Content)
        m_TextViewLog.setMovementMethod(ScrollingMovementMethod())

        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_PointMallDetail)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

        // 페이지별 제목 표시 (가운데 정렬) 네비게이션 앱바
        val tv = findViewById<TextView>(R.id.navi_title_center)
        tv.setText("포인트몰 상세페이지")
    }

    // (수정,추가_백엔드) 선택한 상품 정보 보여주기
    fun shopItemInfoView(dto:ShopDto, context:Context){
        val title = findViewById<TextView>(R.id.pointDetail_tv_Title)
        val content = findViewById<TextView>(R.id.pointDetail_tv_Content)
        val photo = findViewById<ImageView>(R.id.pointDetail_iv_Image)

        // 바인딩
        title.text = dto.title
        content.text = dto.content
        if (dto.photo != "") {
            val resourceId = context.resources.getIdentifier(dto.photo, "drawable", context.packageName)
            if (resourceId > 0){
                photo.setImageResource(resourceId)
            }else{
                photo.setBackgroundResource(0)
                Glide.with(context).load(dto.photo).into(photo)
            }
        }else{
            photo.setImageResource((R.mipmap.ic_launcher_round))
        }

    }




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
            R.id.menu_manager->  {
                val i = Intent(this, ManagerMenuActivity::class.java)
                startActivity(i)
            }
            R.id.menu_food-> {
                val i = Intent(this, FoodActivity::class.java)
                startActivity(i)
            }
            R.id.menu_logout-> {
                androidx.appcompat.app.AlertDialog.Builder(this)
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