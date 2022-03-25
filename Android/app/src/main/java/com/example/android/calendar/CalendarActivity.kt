package com.example.android.calendar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_calendar.*
import java.io.FileInputStream
import java.io.FileOutputStream

class CalendarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


    var fname: String = ""
    var str: String = ""





    // 데이터 확인용 변수로 지워도 됩니다
    var userList = arrayListOf<CalendarDto>(
        CalendarDto("againsa", "2022-06-08", "오후","2일 휴무"),
        CalendarDto("bstro", "2022-08-08", "새벽","1일 휴무"),
        CalendarDto("cvbk", "2023-02-08", "오전","1일 휴무"),

    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)




        // 달력 날짜가 선택되면
        datetitle.visibility = View.VISIBLE // 해당 날짜가 뜨는 textView가 Visible
        calSaveBtn.visibility = View.VISIBLE // 저장 버튼이 Visible
        contextEditText.visibility = View.VISIBLE // EditText가 Visible
        calUpdateBtn.visibility = View.INVISIBLE // 수정 Button이 Invisible
        calDeleteBtn.visibility = View.INVISIBLE // 삭제 Button이 Invisible


        calCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
// 달력 날짜가 선택되면
            datetitle.visibility = View.VISIBLE // 해당 날짜가 뜨는 textView가 Visible
            calSaveBtn.visibility = View.VISIBLE // 저장 버튼이 Visible
            contextEditText.visibility = View.VISIBLE // EditText가 Visible
            calUpdateBtn.visibility = View.INVISIBLE // 수정 Button이 Invisible
            calDeleteBtn.visibility = View.INVISIBLE // 삭제 Button이 Invisible

            datetitle.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
// 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
            contextEditText.setText("") // EditText에 공백값 넣기

            checkedDay(year, month, dayOfMonth) // checkedDay 메소드 호출


        }

        calSaveBtn.setOnClickListener { // 저장 Button이 클릭되면
            saveDiary(fname) // saveDiary 메소드 호출
            str = contextEditText.getText().toString() // str 변수에 edittext내용을 toString
//형으로 저장
            datetitle.text = "${str}" // textView에 str 출력
            calSaveBtn.visibility = View.INVISIBLE
            calUpdateBtn.visibility = View.VISIBLE
            calDeleteBtn.visibility = View.VISIBLE
            contextEditText.visibility = View.INVISIBLE
            datetitle.visibility = View.VISIBLE

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
        navigationView = findViewById(R.id.nav_Calendar)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

/*


        // 리사이클러 뷰
        var calRecyclerView = findViewById<RecyclerView>(R.id.calRecyclerView)           // 달력 일정 입력시 리사이클러뷰 목록 표시
        val mAdapter = CustomAdapterCal(this, userList)
        calRecyclerView.adapter = mAdapter
        var layout = LinearLayoutManager(this)
        calRecyclerView.layoutManager = layout
        calRecyclerView.setHasFixedSize(true)
*/

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


    // 달력 날짜 체크
    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
        fname = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"
// 저장할 파일 이름 설정. Ex) 2019-01-20.txt
        var fis: FileInputStream? = null // FileStream fis 변수 설정

        try {
            fis = openFileInput(fname) // fname 파일 오픈!!

            val fileData = ByteArray(fis.available()) // fileData에 파이트 형식 //으로 저장
            fis.read(fileData) // fileData를 읽음
            fis.close()

            str = String(fileData) // str 변수에 fileData를 저장

            contextEditText.visibility = View.INVISIBLE
            datetitle.visibility = View.VISIBLE
            datetitle.text = "${str}" // textView에 str 출력

            calSaveBtn.visibility = View.INVISIBLE
            calUpdateBtn.visibility = View.VISIBLE
            calDeleteBtn.visibility = View.VISIBLE

            calUpdateBtn.setOnClickListener { // 수정 버튼을 누를 시
                contextEditText.visibility = View.VISIBLE
                datetitle.visibility = View.INVISIBLE
                contextEditText.setText(str) // editText에 textView에 저장된
// 내용을 출력
                calSaveBtn.visibility = View.VISIBLE
                calUpdateBtn.visibility = View.INVISIBLE
                calDeleteBtn.visibility = View.INVISIBLE
                datetitle.text = "${contextEditText.getText()}"
            }

            calDeleteBtn.setOnClickListener {
                datetitle.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                calSaveBtn.visibility = View.VISIBLE
                calUpdateBtn.visibility = View.INVISIBLE
                calDeleteBtn.visibility = View.INVISIBLE
                removeDiary(fname)
            }

            if(datetitle.getText() == ""){
                datetitle.visibility = View.INVISIBLE
//                diaryTextView.visibility = View.VISIBLE
                calSaveBtn.visibility = View.VISIBLE
                calUpdateBtn.visibility = View.INVISIBLE
                calDeleteBtn.visibility = View.INVISIBLE
                contextEditText.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    // 일정 저장
    @SuppressLint("WrongConstant")
    fun saveDiary(readyDay: String?) {
        var fos: FileOutputStream? = null

        try {
            fos = openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = contextEditText.getText().toString()
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    // 일정 삭제
    @SuppressLint("WrongConstant")
    fun removeDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }




}
