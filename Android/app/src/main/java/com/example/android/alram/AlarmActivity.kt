package com.example.android.alram

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView
import java.util.*

class AlarmActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        // 뷰를 초기화 해주기
        initOnOffButton()
        initChangeAlarmTimeButton()

        // 저장된 데이터 가져오기
        val model = fetchDataFromSharedPreferences()
        // 뷰에 데이터를 그려주기
        renderView(model)




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


    private fun fetchDataFromSharedPreferences(): AlarmDisplayModel {
        val sharedPreferences = getSharedPreferences(M_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

        // DB 에서 데이터 가져오기
        val timeDBValue = sharedPreferences.getString(M_ALARM_KEY, "09:30") ?: "09:30"
        val onOffDBValue = sharedPreferences.getBoolean(M_ONOFF_KEY, false)

        // 시:분 형식으로 가져온 데이터 스플릿
        val alarmData = timeDBValue.split(":")

        val alarmModel = AlarmDisplayModel(alarmData[0].toInt(), alarmData[1].toInt(), onOffDBValue)

        // 보정 조정 예외처 (브로드 캐스트 가져오기)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            M_ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        ) // 있으면 가져오고 없으면 안만든다. (null)

        if ((pendingIntent == null) and alarmModel.onOff) {
            //알람은 꺼져있는데, 데이터는 켜져있는 경우
            alarmModel.onOff = false

        } else if ((pendingIntent != null) and alarmModel.onOff.not()) {
            // 알람은 켜져있는데 데이터는 꺼져있는 경우.
            // 알람을 취소함
            pendingIntent.cancel()
        }
        return alarmModel
    }

    // 시간 재설정 버튼.
    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.changeAlarmTimeButton)



        changeAlarmButton.setOnClickListener {
            // 현재 시간을 가져오기 위해 캘린더 인스턴스
            val calendar = Calendar.getInstance()
            // TimePickDialog 띄워줘서 시간을 설정을 하게끔 하고, 그 시간을 가져와서
            TimePickerDialog(this, { picker, hour, minute ->


                // 데이터를 저장
                val model = saveAlarmModel(hour, minute, false)
                // 뷰를 업데이트
                renderView(model)

                // 기존에 있던 알람을 삭제한다.
                cancelAlarm()

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                .show()

        }
    }

    // 알람 켜기 끄기 버튼.
    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.onOffButton)

        print("initonoff")

        onOffButton.setOnClickListener {
            print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
            // 저장한 데이터를 확인한다
            val model =
                it.tag as? AlarmDisplayModel ?: return@setOnClickListener// 형변환 실패하는 경우에는 null
            print("=========================" + model)
            val newModel = saveAlarmModel(model.hour, model.minute, model.onOff.not()) // on off 스위칭
            print("ddddddddddddddddddddddddd" + newModel)
            renderView(newModel)

            // 온/오프 에 따라 작업을 처리한다

            if (newModel.onOff) {
                // 온 -> 알람을 등록
                val calender = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, newModel.hour)
                    set(Calendar.MINUTE, newModel.minute)
                    // 지나간 시간의 경우 다음날 알람으로 울리도록
                    if (before(Calendar.getInstance())) {
                        add(Calendar.DATE, 1) // 하루 더하기
                    }
                }

                //알람 매니저 가져오기.
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    M_ALARM_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                // 있으면 새로 만든거로 업데이트
                // 잠자기 모드에서도 허용 하는 방법
//                alarmManager.setAndAllowWhileIdle()
//                alarmManager.setExactAndAllowWhileIdle()

                alarmManager.setInexactRepeating( // 정시에 반복
                    AlarmManager.RTC_WAKEUP, // RTC_WAKEUP : 실제 시간 기준으로 wakeup , ELAPSED_REALTIME_WAKEUP : 부팅 시간 기준으로 wakeup
                    calender.timeInMillis, // 언제 알람이 발동할지.
                    AlarmManager.INTERVAL_DAY, // 하루에 한번씩.
                    pendingIntent
                )
                Toast.makeText(this@AlarmActivity,"알람이 등록되었습니다.",Toast.LENGTH_LONG).show()

            } else {
                // 오프 -> 알람을 제거
                cancelAlarm()
                Toast.makeText(this@AlarmActivity,"알람이 취소되었습니다.",Toast.LENGTH_LONG).show()


            }
        }
    }

    private fun renderView(model: AlarmDisplayModel) {
        // 최초 실행 또는 시간 재설정 시 들어옴

        findViewById<TextView>(R.id.ampmTextView).apply {
            text = model.ampmText
        }
        findViewById<TextView>(R.id.timeTextView).apply {
            text = model.timeText
        }
        findViewById<Button>(R.id.onOffButton).apply {
            text = model.onOffText
            tag = model
        }
    }

    private fun cancelAlarm() {
        // 기존에 있던 알람을 삭제한다.
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            M_ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        ) // 있으면 가져오고 없으면 안만든다. (null)

        pendingIntent?.cancel() // 기존 알람 삭제
    }

    private fun saveAlarmModel(hour: Int, minute: Int, onOff: Boolean): AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = onOff
        )

        // time 에 대한 db 파일 생성
        val sharedPreferences = getSharedPreferences(M_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

        // edit 모드로 열어서 작업 (값 저장)
        with(sharedPreferences.edit()) {
            putString(M_ALARM_KEY, model.makeDataForDB())
            putBoolean(M_ONOFF_KEY, model.onOff)
            commit()
        }

        return model
    }


    companion object {
        // static 영역 (상수 지정)
        private const val M_SHARED_PREFERENCE_NAME = "time"
        private const val M_ALARM_KEY = "alarm"
        private const val M_ONOFF_KEY = "onOff"
        private val M_ALARM_REQUEST_CODE = 1000
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