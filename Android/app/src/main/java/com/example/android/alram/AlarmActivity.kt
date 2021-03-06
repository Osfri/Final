package com.example.android.alram

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.calendar.CalendarDto
import com.example.android.chat.ChatActivity
import com.example.android.lunch.FoodActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.offday.OffDayDao
import com.example.android.offday.OffdayDto
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class AlarmActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    override fun onBackPressed() {
        val i = Intent(this,MainActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    private var alarmManager: AlarmManager? = null
    private var mCalender: GregorianCalendar? = null
    private var notificationManager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val onOffButton = findViewById<View>(R.id.onOffButton) as Button
        val onOffButtonCancle = findViewById<Button>(R.id.onOffButtonCancle)

        //???????????? ??????
        var code:String = MemberDao.user?.code!!
        if (code.contains("_")){
            code = code.split("_")[0]
        }
        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~code~"+code)

        //????????????db ??????(d,m,n)??? ??????(starttime)????????????
        val parttime:List<AlarmDto>? = MemberDao?.getInstance()?.alarmList(code)!!
        val hashmap = HashMap<String,Any>()
        if (parttime != null){
            //??????(d,m,n)??? ???????????? ???????????? ??????
            for (i in parttime!!.indices){
                hashmap.put("${parttime[i].name.toString()}","${parttime[i].starttime!!}")
            }
        }
        //????????? ????????????
        val calList: List<CalendarDto>? = MemberDao.getInstance().calList(MemberDao.user!!.id.toString())

        if (parttime?.size != 0 && calList?.size != 0){
            //?????????????????? ??????
            var alarmlistRecyclerView = findViewById<RecyclerView>(R.id.AlarmRecyclerView)
            val mAdapter = CustomAdapterAlarm(this,calList!!)
            alarmlistRecyclerView.adapter = mAdapter
            val layout = LinearLayoutManager(this)
            alarmlistRecyclerView.layoutManager = layout
            alarmlistRecyclerView.setHasFixedSize(true)

            //????????????
            val receiverIntent = Intent(this@AlarmActivity, AlarmReceiver::class.java)
            var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this@AlarmActivity,999,receiverIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            onOffButton.setOnClickListener{
                for (i in calList!!.indices){
                    // ?????????DTO?????? ??? ??? ??? ??????
                    val calYear = calList[i].wdate.toString().split(" ")[0].split("-")[0].toInt()
                    val calMonth = calList[i].wdate.toString().split(" ")[0].split("-")[1].toInt()
                    val calDay = calList[i].wdate.toString().split(" ")[0].split("-")[2].toInt()
                    // ?????????DTO?????? name(d,m,n) ??? ??????????????? ???????????? ?????? ?????? ????????????
                    println(hashmap.get("${calList[i].time.toUpperCase()}").toString())
                    println(hashmap.get("D").toString())
                    println(calList[i].time)
                    val calHour = hashmap.get("${calList[i].time.toUpperCase()}").toString().split(":")[0].toInt()
                    val calMinute = hashmap.get("${calList[i].time.toUpperCase()}").toString().split(":")[1].toInt()

                    notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    mCalender = GregorianCalendar()

                    //AlarmReceiver??? ??? ??????
                    pendingIntent = PendingIntent.getBroadcast(this@AlarmActivity, i , receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                    //????????????
                    val calendar = Calendar.getInstance()
                    calendar.set(calYear,calMonth-1,calDay,calHour,calMinute,0)

                    alarmManager!![AlarmManager.RTC, calendar.timeInMillis] = pendingIntent
                }
                    Toast.makeText(this,"????????? ?????????????????????",Toast.LENGTH_LONG).show()
            }
            //?????? ??????
            onOffButtonCancle.setOnClickListener {
                pendingIntent.cancel()
                Toast.makeText(this,"????????? ?????????????????????",Toast.LENGTH_SHORT).show()
            }
        }else{
            onOffButton.setOnClickListener{
                Toast.makeText(this,"???????????? ?????? ???????????? ????????????.",Toast.LENGTH_SHORT).show()
            }
            onOffButtonCancle.setOnClickListener {
                Toast.makeText(this,"???????????? ?????? ???????????? ????????????.",Toast.LENGTH_SHORT).show()
            }
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
        navigationView = findViewById(R.id.nav_Alarm)
        navigationView.setNavigationItemSelectedListener(this) //navigation ?????????

        // ???????????? ?????? ?????? (????????? ??????) ??????????????? ??????
        val tv = findViewById<TextView>(R.id.navi_title_center)
        tv.setText("??????")
        //val loginId = findViewById<TextView>(R.id.hamLoginId)
        //loginId.text = "test"
    }


    // drawerlayout ?????????
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
    // drawerlayout ?????????
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_bbs-> {                                                  // ????????????
                val i = Intent(this, BbsActivity::class.java)
                Log.d("??????","??????")
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