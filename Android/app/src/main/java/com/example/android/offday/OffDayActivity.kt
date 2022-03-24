package com.example.android.offday

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.toColorInt
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.*


open class OffDayActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {
    // 임시데이터
    var tempData:MutableMap<String, OffdayDto>
            = mutableMapOf(
        Pair("2022.03.24", OffdayDto("2022.03.24", mutableListOf("박현준", "최성규"))),
        Pair("2022.03.11", OffdayDto("2022.03.11", mutableListOf("김다균"))),
                Pair("2022.03.11", OffdayDto("2022.03.28", mutableListOf("추현지")))
    )
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_off_day)

        //버튼 변수
        val offBtn = findViewById<Button>(R.id.offBtn)              // 신청
        val offBtnCancle = findViewById<Button>(R.id.offBtnCancle)  // 취소









        // 달력일자 생성
        val mCalendarList:MutableList<OffdayDto> = setCalendarList(tempData)

        // 그리드 뷰 생성
        val gridManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)

        // 리사이클러뷰 생성
        val recyclerView = findViewById<RecyclerView>(R.id.offRecyclerView)
        val calendarRecyclerViewAdapter:CalendarRecyclerViewAdapter = CalendarRecyclerViewAdapter(mCalendarList, this)
        recyclerView.adapter = calendarRecyclerViewAdapter
        recyclerView.layoutManager = gridManager


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





    fun setCalendarList(tempData:MutableMap<String, OffdayDto>): MutableList<OffdayDto>{
        var nowDate:GregorianCalendar = GregorianCalendar()   // 오늘 날짜
        var calendarList: MutableList<OffdayDto> = mutableListOf()    // 캘린더 목록("date") + 정보("info")
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

        // TODO: 선택한 일자 전후로 보이게 설정
        // 리사이클러뷰에 보일 날짜 생성
        for(i in 0..12){
            // calendar: 오늘 날짜의 년, 월
            var calendar:GregorianCalendar = GregorianCalendar(nowDate.get(Calendar.YEAR), nowDate.get(Calendar.MONTH)+i,1,0,0,0)
            var date: OffdayDto = OffdayDto(calendar.timeInMillis)
            calendarList.add(date)
            //calendarList.add(sdf.format(calendar.time))

            // dayOfWeek: 1=일, 2=월, 3=화, 4=수, 5=목, 6=금, 7=토
            var dayOfWeek:Int = calendar.get(Calendar.DAY_OF_WEEK) - 1  // 해당 월에 시작하는 요일 -1 = 빈칸

            var maxOfMonth:Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)   // 해당 월의 마지막 요일

            // 해당 월의 빈칸 추가
            for(j in 0 until dayOfWeek){
                var date: OffdayDto = OffdayDto("empty")
                calendarList.add(date)
            }

            // 해당 월의 일자 추가
            for(k in 1..maxOfMonth){
                var day:GregorianCalendar = GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), k)
                var date: OffdayDto = OffdayDto(day)
                calendarList.add(date)
                //calendarList.add(sdf.format(GregorianCalendar(nowDate.get(Calendar.YEAR),nowDate.get(Calendar.MONTH), k).time))
            }
        }

        // 일정 내용 추가
        for((index:Int, dto: OffdayDto) in calendarList.withIndex()){
            if(dto.date !is Long && dto.date !is String){
                if(tempData.containsKey(sdf.format((dto.date as GregorianCalendar).time))){
                    calendarList[index].content = tempData.get(sdf.format((dto.date as GregorianCalendar).time))!!.content
                }
            }
        }

        return  calendarList
    }

    class CalendarRecyclerViewAdapter(val mCalendarList:MutableList<OffdayDto>, val context: Context)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        // 날짜타입
        class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val headText = itemView.findViewById<TextView>(R.id.calendar_item_headText)
            val sdf: SimpleDateFormat = SimpleDateFormat("yyyy년 MM월")
            fun headBind(dto: OffdayDto){
                headText.text = sdf.format(dto.date as Long)
            }
        }
        // 비어있는 날짜타입
        class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        }
        // 요일타입
        class DayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val dayText = itemView.findViewById<TextView>(R.id.calendar_item_dayText)
            val sdf: SimpleDateFormat = SimpleDateFormat("dd")              //  "yyyy.MM.dd"
            fun dayBind(dto: OffdayDto, context: Context){
                dayText.text = sdf.format((dto.date as GregorianCalendar).time)
                if((dto.date as GregorianCalendar).get(Calendar.DAY_OF_WEEK) == 1){
                    dayText.setTextColor(Color.parseColor("#FF4081"))
                }else if((dto.date as GregorianCalendar).get(Calendar.DAY_OF_WEEK) == 7){
                    dayText.setTextColor(Color.parseColor("#448AFF"))
                }else{
                    dayText.setTextColor(Color.parseColor("#000000"))
                }



                if(dto.content.size>0){
                    setContentItem(dto, context)
                }else{
                    var linerLayout = itemView.findViewById<LinearLayout>(R.id.calendar_item_addContent)
                    linerLayout.removeAllViews()
                }
            }

            fun setContentItem(dto: OffdayDto, context:Context){
                var linerLayout = itemView.findViewById<LinearLayout>(R.id.calendar_item_addContent)
                val contentCount = dto.content.size

                for(i in 0 until contentCount){
                    // 텍스트뷰 속성 설정
                    val contentTextViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                    // 텍스트뷰 생성
                    val contentTextView = TextView(context).apply {
                        text = dto.content.get(i)
                        layoutParams = contentTextViewParams
                        id = i
                        gravity = Gravity.CENTER

                        setOnClickListener {
                            val testToast = Toast.makeText(context, "${dto.content.get(i)}님의 off는 ", Toast.LENGTH_SHORT)
                            testToast.show()
                        }
                    }
                    // 텍스트뷰 추가
                    linerLayout.addView(contentTextView)
                }

            }
        }


        // onCreateViewHolder 호출 전 뷰타입 지정
        override fun getItemViewType(position: Int): Int {
            if(mCalendarList[position].date is Long){ // 날짜타입
                return 0
            }else if(mCalendarList[position].date is String) { // 비어있는 날짜타입
                return 1
            }else{  // 요일타입
                return 2
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View
            if(viewType == 0 ){
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_off_header, parent, false)
                // 그리드 레이아웃 1줄로 표시
                val params:StaggeredGridLayoutManager.LayoutParams = (view.rootView.layoutParams as StaggeredGridLayoutManager.LayoutParams)
                params.isFullSpan = true
                view.rootView.layoutParams = params
                return HeaderViewHolder(view)
            }else if(viewType == 1){
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_off_empty, parent, false)
                return EmptyViewHolder(view)
            }else{
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_off_day, parent, false)
                return DayViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(mCalendarList[position].date is Long){ // 날짜타입
                (holder as HeaderViewHolder).headBind(mCalendarList[position])
            }else if(mCalendarList[position].date is String) { // 비어있는 날짜타입

            }else{  // 요일타입
                (holder as DayViewHolder).dayBind(mCalendarList[position], context)
            }
        }

        override fun getItemCount(): Int {
            return mCalendarList.size
        }



    }
}