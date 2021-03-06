package com.example.android.manager.staff

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.lunch.FoodActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.manager.bbs.CustomAdapterManagerBbs
import com.example.android.manager.bbs.ManagerBbsDto
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import com.google.android.material.navigation.NavigationView

class ManagerStaffActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    override fun onBackPressed() {
        val main = Intent(this, MainActivity::class.java)
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_staff)
        var allmember:ArrayList<MemberDto>? = null
        var waitmember:ArrayList<MemberDto>? = null
        var split = MemberDao.user?.code!!
        if (MemberDao.user?.code!!.contains("_")){
            split = MemberDao.user?.code!!.split("_")[0]
        }
        println(split)
        try {
            val all:ArrayList<MemberDto>? = MemberDao.getInstance().allmember(split)
            val wait:ArrayList<MemberDto>? = MemberDao.getInstance().waitmember(split)
            println(all.toString())
            println(wait.toString())
            allmember = all
            waitmember = wait
        }catch (e:Exception){

        }
        println(allmember.toString())
        println(waitmember.toString())
        if (allmember != null){
            // ??????????????? ??? - ?????? ????????????
            var managerRecyclerView = findViewById<RecyclerView>(R.id.managerStaffMemRecyclerView)
            val mAdapter = CustomAdapterManagerStaff(this, allmember!!)
            managerRecyclerView.adapter = mAdapter
            var layout = LinearLayoutManager(this)
            managerRecyclerView.layoutManager = layout
            managerRecyclerView.setHasFixedSize(true)
        }

        if (waitmember != null){
            // ??????????????? ??? - ????????????
            var managerStaffJoinRecyclerView = findViewById<RecyclerView>(R.id.managerStaffJoinRecyclerView)
            val mAdapterJoin = CustomAdapterManagerStaffJoin(this, waitmember!!)
            managerStaffJoinRecyclerView.adapter = mAdapterJoin
            var layoutJoin = LinearLayoutManager(this)
            managerStaffJoinRecyclerView.layoutManager = layoutJoin
            managerStaffJoinRecyclerView.setHasFixedSize(true)
        }















        // drawerlayout bar ??????
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar??? ?????? App Bar ??????
        setSupportActionBar(toolbar) // ?????? ??????
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // ???????????? ?????? ??? ?????? ?????????
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // ????????? ????????? ??????
        supportActionBar?.setDisplayShowTitleEnabled(false) // ????????? ????????? ????????????
        // ??????????????? ????????? ??????
        drawerLayout = findViewById(R.id.drawer_layout)

        // ??????????????? ????????? ???????????? ????????? ???????????? ???????????? ?????? ??????
        navigationView = findViewById(R.id.nav_Manager_staff)
        navigationView.setNavigationItemSelectedListener(this) //navigation ?????????

        // ???????????? ?????? ?????? (????????? ??????) ??????????????? ??????
        val tv = findViewById<TextView>(R.id.navi_title_center)
        tv.setText("??????????????????")




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
            R.id.menu_food-> {
                val i = Intent(this, FoodActivity::class.java)
                startActivity(i)
            }
            R.id.menu_logout-> {
                AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("???????????? ???????????????????")
                    .setPositiveButton("???", DialogInterface.OnClickListener { dialog, which ->
                        val i  = Intent(this, SigninActivity::class.java)
                        val dto = MemberDto("", "", "","","","",0,0,0,0)
                        MemberDao.user = dto
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(i)
                    })
                    .setNegativeButton("?????????", null)
                    .create()
                    .show()
            }
        }
        return false
    }



}