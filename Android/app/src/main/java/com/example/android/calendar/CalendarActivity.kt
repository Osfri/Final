package com.example.android.calendar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.offday.OffdayDto
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {
    // 임시데이터
//    var tempData:MutableMap<String, CalendarDto>
//            = mutableMapOf(
//        Pair("2022.03.24", CalendarDto("2022.03.24", mutableListOf("박현준", "최성규"))),
//        Pair("2022.03.11", CalendarDto("2022.03.11", mutableListOf("김다균"))),
//        Pair("2022.03.11", CalendarDto("2022.03.28", mutableListOf("추현지")))
//    )
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        var curData:MutableMap<String, CalendarDto>? = null
        val mem = MemberDao.user
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df = SimpleDateFormat("yyyy-MM")
        //현재 시간 기준으로 다음 달
        cal.add(Calendar.MONTH, 1)
        val dto = CalendarDto(mem!!.id.toString(), df.format(cal.time).toString())
        //db에 저장된 본인 일정 모두 불러옴
        val dutyList: List<CalendarDto>? = CalendarDao.getInstance().dutyList(dto)

        //데이터 형태에 맞게 저장
        if (dutyList != null && dutyList.size != 0) {
            for(i in dutyList!!){
                val dto = CalendarDto(i.wdate.toString(), i.time, i.id, i.memo)
                if(curData == null){
                    curData = mutableMapOf(Pair(i.wdate.toString(), dto))
                }else{
                    curData[i.wdate.toString()] = dto
                }
            }
        }else{  //db에 데이터가 없는 경우 처리
            val dto = CalendarDto("", "", "", "")
            curData = mutableMapOf(Pair("2022-03", dto))
        }

        val calEt = findViewById<EditText>(R.id.cal_et)
        val calSaveBtn = findViewById<Button>(R.id.calSaveBtn)      // 저장버튼
        val calUpdateBtn = findViewById<Button>(R.id.calUpdateBtn)  // 수정버튼
        val calDeleteBtn = findViewById<Button>(R.id.calDeleteBtn)  // 저장버튼
        calSaveBtn.setOnClickListener {
            if(CalendarDao.memoDate == null){
                Toast.makeText(this, "날짜를 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }


        // 달력일자 생성
        val mCalendarList:MutableList<CalendarDto> = setCalendarList(curData!!)

        // 그리드 뷰 생성
        val gridManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)

        // 리사이클러뷰 생성
        val recyclerView = findViewById<RecyclerView>(R.id.calRecyclerView)
        val calendarRecyclerViewAdapter:CalRecyclerViewAdapter = CalRecyclerViewAdapter(mCalendarList, this)
        recyclerView.adapter = calendarRecyclerViewAdapter
        //calendarRecyclerViewAdapter.notifyDataSetChanged()
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
        navigationView = findViewById(R.id.nav_Calendar)
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

    fun setCalendarList(tempData:MutableMap<String, CalendarDto>): MutableList<CalendarDto>{
        var nowDate: GregorianCalendar = GregorianCalendar()   // 오늘 날짜
        var calendarList: MutableList<CalendarDto> = mutableListOf()    // 캘린더 목록("date") + 정보("info")
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

        // TODO: 선택한 일자 전후로 보이게 설정

        // 리사이클러뷰에 보일 날짜 생성
        for(i in -6..6){
            // calendar: 오늘 날짜의 년, 월
            var calendar: GregorianCalendar = GregorianCalendar(nowDate.get(Calendar.YEAR), nowDate.get(Calendar.MONTH)+i,1,0,0,0)
            var date: CalendarDto = CalendarDto(calendar.timeInMillis)
            calendarList.add(date)
            //calendarList.add(sdf.format(calendar.time))

            // dayOfWeek: 1=일, 2=월, 3=화, 4=수, 5=목, 6=금, 7=토
            var dayOfWeek:Int = calendar.get(Calendar.DAY_OF_WEEK) - 1  // 해당 월에 시작하는 요일 -1 = 빈칸

            var maxOfMonth:Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)   // 해당 월의 마지막 요일

            // 해당 월의 빈칸 추가
            for(j in 0 until dayOfWeek){
                var date: CalendarDto = CalendarDto("empty")
                calendarList.add(date)
            }

            // 해당 월의 일자 추가
            for(k in 1..maxOfMonth){
                var day: GregorianCalendar = GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), k)
                var date: CalendarDto = CalendarDto(day)
                calendarList.add(date)
                //calendarList.add(sdf.format(GregorianCalendar(nowDate.get(Calendar.YEAR),nowDate.get(Calendar.MONTH), k).time))
            }
        }

        // 일정 내용 추가
        for((index:Int, dto: CalendarDto) in calendarList.withIndex()){
            if(dto.wdate !is Long && dto.wdate !is String){
                if(tempData.containsKey(sdf.format((dto.wdate as GregorianCalendar).time))){
                    calendarList[index].content = tempData.get(sdf.format((dto.wdate as GregorianCalendar).time))!!
                    println(dto.wdate)
                }
            }
        }

        return  calendarList
    }

    class CalRecyclerViewAdapter(val mCalendarList:MutableList<CalendarDto>, val context: Context)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        // 날짜타입
        class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val headText = itemView.findViewById<TextView>(R.id.cal_item_headText)
            val sdf: SimpleDateFormat = SimpleDateFormat("yyyy년 MM월")
            fun headBind(dto: CalendarDto){
                headText.text = sdf.format(dto.wdate as Long)
            }
        }
        // 비어있는 날짜타입
        class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        }
        // 요일타입
        class DayViewHolder(itemView: View, val context: Context): RecyclerView.ViewHolder(itemView){
            val dayText = itemView.findViewById<TextView>(R.id.cal_item_dayText)
            val sdf: SimpleDateFormat = SimpleDateFormat("dd")              //  "yyyy.MM.dd"
            fun dayBind(dto: CalendarDto, context: Context){

                dayText.text = sdf.format((dto.wdate as GregorianCalendar).time)
                if((dto.wdate as GregorianCalendar).get(Calendar.DAY_OF_WEEK) == 1){
                    dayText.setTextColor(Color.parseColor("#FF4081"))
                }else if((dto.wdate as GregorianCalendar).get(Calendar.DAY_OF_WEEK) == 7){
                    dayText.setTextColor(Color.parseColor("#448AFF"))
                }else{
                    dayText.setTextColor(Color.parseColor("#000000"))
                }
                if(dto.content != null){
                    setContentItem(dto, context)
                }else{
                    var img = itemView.findViewById<ImageView>(R.id.cal_item_imageView)
                    val memoImg = itemView.findViewById<ImageView>(R.id.cal_item_memoimg)
                    img.setImageResource(R.drawable.ic_cal_zz)
                    memoImg.setImageResource(R.drawable.ic_cal_white)
                }
            }

            fun setContentItem(dto: CalendarDto, context: Context){
                val activity = context as Activity
                val img = itemView.findViewById<ImageView>(R.id.cal_item_imageView)
                val memoImg = itemView.findViewById<ImageView>(R.id.cal_item_memoimg)
                val et = activity.findViewById<EditText>(R.id.cal_et)
                val calSaveBtn = activity.findViewById<Button>(R.id.calSaveBtn)
                val calUpdateBtn = activity.findViewById<Button>(R.id.calUpdateBtn)
                val calDeleteBtn = activity.findViewById<Button>(R.id.calDeleteBtn)
                val tv = activity.findViewById<TextView>(R.id.cal_tv)

                //근무표에 맞는 이미지 넣기
                when(dto.content!!.time){
                    "o"-> img.setImageResource(R.drawable.ic_cal_o)
                    "O"-> img.setImageResource(R.drawable.ic_cal_o)
                    "e"-> img.setImageResource(R.drawable.ic_cal_e)
                    "E"-> img.setImageResource(R.drawable.ic_cal_e)
                    "d"-> img.setImageResource(R.drawable.ic_cal_d)
                    "D"-> img.setImageResource(R.drawable.ic_cal_d)
                    "n"-> img.setImageResource(R.drawable.ic_cal_n)
                    "N"-> img.setImageResource(R.drawable.ic_cal_n)
                    "m"-> img.setImageResource(R.drawable.ic_cal_m)
                    "M"-> img.setImageResource(R.drawable.ic_cal_m)
                }

                //메모 없는 경우 => 초기화를 위해 해줘야 함
                if(dto.content!!.memo == null  || dto.content!!.memo == ""){
                    memoImg.setImageResource(R.drawable.ic_cal_white)
                }else{      //메모 있는 경우
                    memoImg.setImageResource(R.drawable.ic_cal_me)
                }
                calSaveBtn.setOnClickListener {
                    if(CalendarDao.memoDate == null){
                        Toast.makeText(context, "날짜를 선택해 주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                img.setOnClickListener{
                    CalendarDao.memoDate = dto.content!!
                    tv.text = CalendarDao.memoDate!!.wdate.toString()
                    //저장된 메모가 없는 경우 새로 등록
                    if(CalendarDao.memoDate!!.memo == null || CalendarDao.memoDate!!.memo.toString() == ""){
                        et.setText("")
                        calUpdateBtn.visibility = View.INVISIBLE
                        calDeleteBtn.visibility = View.INVISIBLE
                        calSaveBtn.visibility = View.VISIBLE
                        calSaveBtn.setOnClickListener {
                            if(et.text.toString() == ""){
                                Toast.makeText(context, "일정을 입력해주세요", Toast.LENGTH_SHORT).show()
                            }else{
                                CalendarDao.memoDate!!.memo = et.text.toString()
                                val result = CalendarDao.getInstance().memoInsert(CalendarDao.memoDate!!)
                                if(result == "success"){
                                    Toast.makeText(context, "메모가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                                    //화면 새로고침
                                    val intent = (context as Activity).intent
                                    context.finish() //현재 액티비티 종료 실시
                                    context.overridePendingTransition(0, 0) //효과 없애기
                                    context.startActivity(intent) //현재 액티비티 재실행 실시
                                    context.overridePendingTransition(0, 0) //효과 없애기
                                }
                            }
                        }
                    }else{      //저장된 메모가 있는 경우 수정/삭제 기능
                        et.setText(CalendarDao.memoDate!!.memo.toString())
                        calUpdateBtn.visibility = View.VISIBLE
                        calDeleteBtn.visibility = View.VISIBLE
                        calSaveBtn.visibility = View.INVISIBLE
                        calUpdateBtn.setOnClickListener{
                            if(et.text.toString() == ""){
                                Toast.makeText(context, "일정을 입력해주세요", Toast.LENGTH_SHORT).show()
                            }else{
                                CalendarDao.memoDate!!.memo = et.text.toString()
                                val result = CalendarDao.getInstance().memoInsert(CalendarDao.memoDate!!)
                                if(result == "success"){
                                    Toast.makeText(context, "메모가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                                    //화면 새로고침
                                    val intent = (context as Activity).intent
                                    context.finish() //현재 액티비티 종료 실시
                                    context.overridePendingTransition(0, 0) //효과 없애기
                                    context.startActivity(intent) //현재 액티비티 재실행 실시
                                    context.overridePendingTransition(0, 0) //효과 없애기
                                }
                            }
                        }
                        calDeleteBtn.setOnClickListener{
                            AlertDialog.Builder(context).setTitle("MEMO 삭제")
                                .setMessage("${CalendarDao.memoDate!!.wdate}의 메모를 삭제하시겠습니까?")
                                .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                                    CalendarDao.memoDate!!.memo = ""
                                    val result = CalendarDao.getInstance().memoInsert(CalendarDao.memoDate!!)
                                    if(result == "success"){
                                        Toast.makeText(context, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                        //화면 새로고침
                                        val intent = (context as Activity).intent
                                        context.finish() //현재 액티비티 종료 실시
                                        context.overridePendingTransition(0, 0) //효과 없애기
                                        context.startActivity(intent) //현재 액티비티 재실행 실시
                                        context.overridePendingTransition(0, 0) //효과 없애기
                                    }
                                })
                                .setNegativeButton("아니오", null)
                                .show()
                        }
                    }

                }
            }
        }

        // onCreateViewHolder 호출 전 뷰타입 지정
        override fun getItemViewType(position: Int): Int {
            if(mCalendarList[position].wdate is Long){ // 날짜타입
                return 0
            }else if(mCalendarList[position].wdate is String) { // 비어있는 날짜타입
                return 1
            }else{  // 요일타입
                return 2
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View
            if(viewType == 0 ){
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_cal_header, parent, false)
                // 그리드 레이아웃 1줄로 표시
                val params: StaggeredGridLayoutManager.LayoutParams = (view.rootView.layoutParams as StaggeredGridLayoutManager.LayoutParams)
                params.isFullSpan = true
                view.rootView.layoutParams = params
                return HeaderViewHolder(view)
            }else if(viewType == 1){
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_cal_empty, parent, false)
                return EmptyViewHolder(view)
            }else{
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_cal_day, parent, false)
                return DayViewHolder(view, context)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(mCalendarList[position].wdate is Long){ // 날짜타입
                (holder as HeaderViewHolder).headBind(mCalendarList[position])
            }else if(mCalendarList[position].wdate is String) { // 비어있는 날짜타입

            }else{  // 요일타입
                (holder as DayViewHolder).dayBind(mCalendarList[position], context)
            }
        }

        override fun getItemCount(): Int {
            return mCalendarList.size
        }
    }
}