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
import com.example.android.R
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AlarmActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    //임의로 알람 날짜와 시간을 지정 - 교대시간을 여기로 받아와야 할듯
    val from = "2022-03-23 10:55:00"

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


    private var alarmManager: AlarmManager? = null
    private var mCalender: GregorianCalendar? = null

    private var notificationManager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)



        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        mCalender = GregorianCalendar()

        Log.v("AlarmActivity", mCalender!!.getTime().toString())

        setContentView(R.layout.activity_alarm)

        val ampmTextView = findViewById<TextView>(R.id.ampmTextView)
        //접수일 알람 버튼
        val onOffButton = findViewById<View>(R.id.onOffButton) as Button
        onOffButton.setOnClickListener{

            setAlarm()
            Toast.makeText(this,"알람이 설정되었습니다",Toast.LENGTH_LONG).show()
            ampmTextView.text = "$from 에 알람이 울립니다"
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

    private fun setAlarm(){
        //AlarmReceiver에 값 전달
        val receiverIntent = Intent(this@AlarmActivity, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this@AlarmActivity, 0, receiverIntent, 0)



        //날짜 포맷을 바꿔주는 소스코드
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var datetime: Date? = null
        try {
            datetime = dateFormat.parse(from)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = datetime

        alarmManager!![AlarmManager.RTC, calendar.timeInMillis] = pendingIntent
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