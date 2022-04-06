package com.example.android.bbs

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate

class BbsWrite : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    //STORAGE권한 처리에 필요한 변수 - Manifest 부분의 안드로이드 부분이 필요
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    // Db에 넣을 사진 주소
    companion object {
        var imageAddr = ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_write)

        // 카메라
        val camera = findViewById<ImageButton>(R.id.bbsWriteCamera)
        camera.setOnClickListener {
            CallCamera()
        }
        // 사진 저장
        val picture = findViewById<ImageButton>(R.id.bbsWriteImage)
        picture.setOnClickListener {
            GetAlbum()
        }
        // 게시판 타입 데이터 가져오기
        val type:Int = BbsActivity.type
        var typename:String = BbsActivity.typename
        if (typename == ""){
            typename = "공지사항"
        }

        val bbsWriteTitle = findViewById<EditText>(R.id.bbsWriteTitle)
        val bbsWriteId = findViewById<TextView>(R.id.bbsWriteId)
        val bbsWriteContent = findViewById<EditText>(R.id.bbsWriteContent)
        val btn_bbsWriteFin = findViewById<Button>(R.id.btn_bbsWriteFin)
        bbsWriteId.text = "작성자 : ${MemberDao.user?.id} 게시판: ${typename}"

        // bbsWrite 글추가완료 버튼
        btn_bbsWriteFin.setOnClickListener {

            val onlyDate:LocalDate = LocalDate.now() // 현재날짜
            val split = MemberDao.user!!.code!!.split("_")
            val trueCode:String = split[0] // 병동코드 123_1 -> 병원코드 123 변환
            //db쿼리문
            // seq , id , title , content , readCount , wdate , del , type , code , step , group , image
            BbsDao.getInstance().bbswrite(BbsDto(0 , //seq
                                                    MemberDao.user!!.id , //id
                                                    bbsWriteTitle.text.toString() , //title
                                                    bbsWriteContent.text.toString() , //content
                                                    0 , //readCount
                                                    onlyDate.toString() , //wdate
                                                     0 , //del
                                                    type , //type
                                                    trueCode , //code
                                                    0 , //step
                                                    0 , //group
                                                    imageAddr)) //image

            Toast.makeText(this, "추가되었습니다", Toast.LENGTH_LONG).show()

            val i = Intent(this, BbsActivity::class.java)
            startActivity(i)
        }




        //=======================================Front=================================================================
        // drawerlayout bar 설정
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_Bbs_Write)
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



    // 카메라 권한, 저장소 권한
    // 요청 권한
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMERA_CODE -> {
                for (grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "카메라 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
            STORAGE_CODE -> {
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "저장소 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    // 다른 권한등도 확인이 가능하도록
    fun checkPermission(permissions: Array<out String>, type:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (permission in permissions){
                if(ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, permissions, type)
                    return false
                }
            }
        }
        return true
    }

    // 카메라 촬영 - 권한 처리
    fun CallCamera(){
        if(checkPermission(CAMERA, CAMERA_CODE) && checkPermission(STORAGE, STORAGE_CODE)){
            val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(itt, CAMERA_CODE)
        }
    }

    // 사진 저장
    fun saveFile(fileName:String, mimeType:String, bitmap: Bitmap): Uri?{

        var CV = ContentValues()

        // MediaStore 에 파일명, mimeType 을 지정
        CV.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        // 안정성 검사
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CV.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        // MediaStore 에 파일을 저장
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV)
        if(uri != null){
            var scriptor = contentResolver.openFileDescriptor(uri, "w")

            val fos = FileOutputStream(scriptor?.fileDescriptor)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                CV.clear()
                // IS_PENDING 을 초기화
                CV.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, CV, null, null)
            }
        }
        return uri
    }

    // 결과
    @SuppressLint("WrongViewCast")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = findViewById<ImageView>(R.id.bbsWriteImageView)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                //이미지 경로 얻어옴
                CAMERA_CODE -> {
                    if(data?.extras?.get("data") != null){
                        val img = data?.extras?.get("data") as Bitmap
                        val uri = saveFile(RandomFileName(), "image/jpeg", img)
                        imageView.setImageURI(uri)
                        println("이미지경로:$uri")
                        println("실제 이미지경로      :" + getPath(uri))
                        imageAddr = uri.toString()
                    }
                }
                STORAGE_CODE -> {
                    val uri = data?.data
                    imageView.setImageURI(uri)
                    imageAddr = uri.toString()
                }
            }
        }
    }

    // 파일명을 날짜 저장
    fun RandomFileName() : String{
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        return fileName
    }

    // 갤러리 취득
    fun GetAlbum(){
        if(checkPermission(STORAGE, STORAGE_CODE)){
            val itt = Intent(Intent.ACTION_PICK)
            itt.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(itt, STORAGE_CODE)
        }
    }

    //uri 들어오면 실제 패스명으로 집어넣는다 > 실제 경로 주소로 바꿔주는 함수
    fun getPath(uri: Uri?): String {
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }



}