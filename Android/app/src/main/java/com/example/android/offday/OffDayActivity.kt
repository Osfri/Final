package com.example.android.offday

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView
import com.prolificinteractive.materialcalendarview.*
import java.util.*


open class OffDayActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    private val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_off_day)

        val offCalendarView = findViewById<CalendarView>(R.id.offCalendarView)      // 달력
        val offTextView = findViewById<TextView>(R.id.offTextView)                  // 오프 등록시 날짜 누르면 textview에 목록 표시
        val offSaveBtn = findViewById<Button>(R.id.offSaveBtn)                      // 오프 등록 버튼

        val calendarView = findViewById<MaterialCalendarView>(R.id.offMaterialCalendarView)
        calendarView.setSelectedDate(CalendarDay.today())

        var startTimeCalendar = Calendar.getInstance()
        var endTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)

        endTimeCalendar.set(Calendar.MONTH, currentMonth+3)

        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear, currentMonth, 1))
            .setMaximumDate(CalendarDay.from(currentYear, currentMonth+100, endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        val stCalendarDay = CalendarDay(currentYear, currentMonth, currentDate)
        val enCalendarDay = CalendarDay(endTimeCalendar.get(Calendar.YEAR), endTimeCalendar.get(Calendar.MONTH), endTimeCalendar.get(Calendar.DATE))

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val minMaxDecorator = MinMaxDecorator(stCalendarDay, enCalendarDay)
//        val boldDecorator = BoldDecorator(stCalendarDay, enCalendarDay)
        val todayDecorator = TodayDecorator(this)

        calendarView.addDecorators(sundayDecorator, saturdayDecorator,  todayDecorator) //, minMaxDecorator boldDecorator,





        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Offday)
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
        }
        return false
    }



    // 커스텀 캘린더


    // 오늘 날짜에 도형 표시
    class TodayDecorator(context:Context):DayViewDecorator {
        private var date = CalendarDay.today()
        val drawable = context.resources.getDrawable(R.drawable.button_round)
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day?.equals(date)!!
        }
        override fun decorate(view: DayViewFacade?) {
            view?.setBackgroundDrawable(drawable)
        }
    }
    // 일요일 빨간색 표시
    class SundayDecorator:DayViewDecorator {
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            day?.copyTo(calendar)

            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return weekDay == Calendar.SUNDAY
        }
        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(object:ForegroundColorSpan(Color.RED){})
        }
    }

    //토요일 파란색 표시
    class SaturdayDecorator:DayViewDecorator {
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            day?.copyTo(calendar)
            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return weekDay == Calendar.SATURDAY
        }
        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(object:ForegroundColorSpan(Color.BLUE){})
        }
    }

    class MinMaxDecorator(min:CalendarDay, max:CalendarDay):DayViewDecorator {
        val maxDay = max
        val minDay = min
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return (day?.month == maxDay.month && day.day > maxDay.day)
                    || (day?.month == minDay.month && day.day < minDay.day)
        }
        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#c3c3c3")){})
           // view?.setDaysDisabled(true)
        }
    }

    // 오늘 기준으로 이전 날짜 보다 글씨 크기가 굵어짐
/*    class BoldDecorator(min:CalendarDay, max:CalendarDay):DayViewDecorator {
        val maxDay = max
        val minDay = min
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return (day?.month == maxDay.month && day.day <= maxDay.day)
                    || (day?.month == minDay.month && day.day >= minDay.day)
                    || (minDay.month < day?.month!! && day.month < maxDay.month)
        }
        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(object:StyleSpan(Typeface.NORMAL){})
            view?.addSpan(object:RelativeSizeSpan(1.4f){})
        }
    }*/
}