package com.example.android.alram

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
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
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
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

        //병동코드 제거
        var code:String = MemberDao.user?.code!!
        if (code.contains("_")){
            code = code.split("_")[0]
        }

        //파트타임db 이름(d,m,n)과 시간(starttime)가져오기
        val parttime:List<AlarmDto>? = MemberDao?.getInstance()?.alarmList(code)!!
        val hashmap = HashMap<String,Any>()
        if (parttime != null){
            //이름(d,m,n)의 시간값을 해쉬맵에 저장
            for (i in parttime!!.indices){
                hashmap.put("${parttime[i].name.toString()}","${parttime[i].starttime!!}")
            }
        }
        //캘린더 가져오기
        val calList: List<CalendarDto>? = MemberDao.getInstance().calList(MemberDao.user!!.id.toString())

        if (parttime != null && calList != null){
            //리사이클러뷰 설정
            var alarmlistRecyclerView = findViewById<RecyclerView>(R.id.AlarmRecyclerView)
            val mAdapter = CustomAdapterAlarm(this,calList!!)
            alarmlistRecyclerView.adapter = mAdapter
            val layout = LinearLayoutManager(this)
            alarmlistRecyclerView.layoutManager = layout
            alarmlistRecyclerView.setHasFixedSize(true)

            //알람설정
            val receiverIntent = Intent(this@AlarmActivity, AlarmReceiver::class.java)
            var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this@AlarmActivity,999,receiverIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            onOffButton.setOnClickListener{
                for (i in calList!!.indices){
                    // 캘린더DTO에서 연 월 일 분리
                    val calYear = calList[i].wdate.toString().split(" ")[0].split("-")[0].toInt()
                    val calMonth = calList[i].wdate.toString().split(" ")[0].split("-")[1].toInt()
                    val calDay = calList[i].wdate.toString().split(" ")[0].split("-")[2].toInt()
                    // 캘린더DTO에서 name(d,m,n) 을 파트타임과 매칭시켜 맞는 시간 가져오기
                    println(hashmap.get("${calList[i].time.toUpperCase()}").toString())
                    println(hashmap.get("D").toString())
                    println(calList[i].time)
                    val calHour = hashmap.get("${calList[i].time.toUpperCase()}").toString().split(":")[0].toInt()
                    val calMinute = hashmap.get("${calList[i].time.toUpperCase()}").toString().split(":")[1].toInt()

                    notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    mCalender = GregorianCalendar()

                    //AlarmReceiver에 값 전달
                    pendingIntent = PendingIntent.getBroadcast(this@AlarmActivity, i , receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                    //시간설정
                    val calendar = Calendar.getInstance()
                    calendar.set(calYear,calMonth-1,calDay,calHour,calMinute,0)

                    alarmManager!![AlarmManager.RTC, calendar.timeInMillis] = pendingIntent
                }
                    Toast.makeText(this,"알람이 설정되었습니다",Toast.LENGTH_LONG).show()
            }
            //취소 버튼
            onOffButtonCancle.setOnClickListener {
                pendingIntent.cancel()
                Toast.makeText(this,"알람이 취소되었습니다",Toast.LENGTH_SHORT).show()
            }
        }else{
            onOffButton.setOnClickListener{
                Toast.makeText(this,"설정할수 있는 스케줄이 없습니다.",Toast.LENGTH_SHORT).show()
            }
            onOffButtonCancle.setOnClickListener {
                Toast.makeText(this,"취소할수 있는 스케줄이 없습니다.",Toast.LENGTH_SHORT).show()
            }
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
        navigationView = findViewById(R.id.nav_Alarm)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

    }




    // drawerlayout 네비바
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
    // drawerlayout 네비바
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
        }
        return false
    }
}