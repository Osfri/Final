package com.example.android.bbs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
        startActivity(i)
    }
    companion object {
        /*var BbsDetail: BbsDetail? = null

        fun getInstance(): BbsDetail {
            if (BbsDetail == null) {
                BbsDetail = BbsDetail()
            }
            return BbsDetail!!
        }
*/
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
        // ????????? ??? ?????? ??????

        var bbsDetailCommentRecycleview = findViewById<RecyclerView>(R.id.bbsDetailCommentRecycleview)  // bbsRecyclerView ??????
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
                .load(Detaildata?.image) // ????????? ????????? url
                .into(bbsDetailImage) // ???????????? ?????? ???
            //bbsDetailImage.setImageURI(Detaildata?.image?.toUri())
        }


        // ????????? ????????? ?????????  (????????????, ?????????, ?????????)
        val btnDetailDel = findViewById<TextView>(R.id.btnDetailDel)                    // ??? ??????
        val btnDetailUpdate = findViewById<TextView>(R.id.btnDetailUpdate)              // ??? ??????
        val bbsCommentEditText = findViewById<EditText>(R.id.bbsCommentEditText)        // ?????? ??????
        val btnCommentWrite = findViewById<Button>(R.id.btnCommentWrite)                // ?????? ?????? ??????

        //?????? - ??????,????????? ?????? - ??????
        if (MemberDao.user?.id.toString() != Detaildata?.id.toString()){
            btnDetailUpdate.visibility= View.GONE
            if (MemberDao.user?.id.toString() != Detaildata?.id.toString() && MemberDao.user?.auth != 0){
                btnDetailDel.visibility=View.GONE
            }
        }

        val bbsCommentRecycleViewDelete = findViewById<Button>(R.id.bbsCommentRecycleViewDelete)    // ?????????????????? ?????? ?????? ??????
        val bbsCommentRecycleViewUpdate = findViewById<Button>(R.id.bbsCommentRecycleViewUpdate)    // ?????????????????? ?????? ?????? ??????
        val bbsCommentRecycleViewWriter = findViewById<TextView>(R.id.bbsCommentRecycleViewWriter)  // ?????????????????? ?????? ????????? ?????? ?????????


        btnCommentWrite.setOnClickListener {
            val onlyDate: String = LocalDate.now().toString()
            val dto = BbsDto(0,MemberDao.user!!.id.toString(),"??????",bbsCommentEditText.text.toString(),0,onlyDate,0,Detaildata!!.type,
                Detaildata!!.code,1,
                Detaildata!!.gr,"")
            BbsDao.getInstance().commentwrite(dto)
            startActivity(Intent(this, BbsDetail::class.java))
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

        // ???????????????

        val mAdapter = CustomAdapterCommentList(this, commentlist())
        test?.adapter = mAdapter
        val layout = LinearLayoutManager(this)
        test?.layoutManager = layout
        test?.setHasFixedSize(true)
        //commentlist(Detaildata!!)



        // bbsDetail -> Bbs ??? ??????
/*        val bbsDetailBack = findViewById<Button>(R.id.bbsDetailBack)
        bbsDetailBack.setOnClickListener {
            val i = Intent(this, BbsActivity::class.java)
            startActivity(i)
        }*/



        // drawerlayout bar ??????
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar??? ?????? App Bar ??????
        setSupportActionBar(toolbar) // ?????? ??????
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // ???????????? ?????? ??? ?????? ?????????
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // ????????? ????????? ??????
        supportActionBar?.setDisplayShowTitleEnabled(false) // ????????? ????????? ????????????
        // ??????????????? ????????? ??????
        drawerLayout = findViewById(R.id.drawer_layout)

        // ??????????????? ????????? ???????????? ????????? ???????????? ???????????? ?????? ??????
        navigationView = findViewById(R.id.nav_Bbs_Detail)
        navigationView.setNavigationItemSelectedListener(this) //navigation ?????????

        // ???????????? ?????? ?????? (????????? ??????) ??????????????? ??????
        val tv = findViewById<TextView>(R.id.navi_title_center)
        tv.setText("?????????")

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val loginId = findViewById<TextView>(R.id.hamLoginId)
        val mid = MemberDao.user!!.name
        loginId.text =mid.toString()+" ???"
        val loginCode = findViewById<TextView>(R.id.hamLoginCode)
        loginCode.text = "???????????????"
        // ????????? ?????? ?????? ????????? id ?????? ????????? ??????????????? ??????
        when(item!!.itemId){
            android.R.id.home->{
                // ????????? ?????? ????????? ??????????????? ????????? ??????
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