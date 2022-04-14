package com.example.android.lunch

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.bbs.BbsActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.offday.OffDayActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import com.google.android.material.navigation.NavigationView
import java.io.FileOutputStream
import java.text.SimpleDateFormat

class FoodWriteActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    var uri:Uri ?= null
    // (수정,추가_백엔드) 카메라 관련
    val getResult:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK)  {
            if(it.data?.extras?.get("data") != null){   // 카메라로 사진을 찍어서 가져오는 경우
                val bitmap:Bitmap = it.data?.extras?.get("data") as Bitmap
                val foodImg:ImageView = findViewById<ImageView>(R.id.foodactivity_foodImg)
                foodImg.setImageBitmap(bitmap)

                uri = savePicture(randomPictureName(), "image/jpeg", bitmap)!!
            }else{  // 갤러리를 통해 사진을 가져온 경우
                uri = it.data?.data!!
                val foodImg:ImageView = findViewById<ImageView>(R.id.foodactivity_foodImg)
                foodImg.setImageURI(uri)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_write)

        // (수정,추가_백엔드) 카메라 기능 추가
        val cameraTopBtn = findViewById<Button>(R.id.btnFoodWriteCamera)
        cameraTopBtn.setOnClickListener {
            callCamera()
        }

        // (수정,추가_백엔드) 갤러리 기능 추가
        val galleryBtn = findViewById<Button>(R.id.btnFoodGallary)
        galleryBtn.setOnClickListener {
            openGallery()
        }

        // (수정,추가_백엔드) 식단 등록 기능 추가
        val foodWriteBtn:Button = findViewById<Button>(R.id.btnFoodWriteAf)
        foodWriteBtn.setOnClickListener {
            val foodMenu:TextView = findViewById<TextView>(R.id.edittextFood)
            var toast:Toast ?= null

            val datePicker:DatePicker = findViewById<DatePicker>(R.id.datepickerDate)
            val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date:String = "${datePicker.year}-${datePicker.month+1}-${datePicker.dayOfMonth}"
            date = sdf.format(sdf.parse(date))

            var addItem:FoodDto


            if(foodMenu.text == null || foodMenu.text.toString().trim() == ""){  // 식단내용을 입력하지 않은 경우
                if(toast != null) toast!!.cancel()
                toast = Toast.makeText(this,"식단을 입력하세요!", Toast.LENGTH_SHORT)
                toast!!.show()
            }else{
                foodWriteBtn.isEnabled = false
                if(uri != null){    // 사진을 등록할 경우
                    uploadImage(uri!!, foodMenu, date)
                }else{  // 사진을 등록하지 않을 경우
                    addItem = FoodDto(MemberDao.user!!.code, date, foodMenu.text.toString(), "")
                    FoodSingleton.getInstance().foodItemAdd(addItem)
                    if(toast != null) toast!!.cancel()
                    toast = Toast.makeText(this,"추가 완료!", Toast.LENGTH_SHORT)
                    toast.show()
                    foodMenu.text = null
                    foodWriteBtn.isEnabled = true
                }
            }
        }


        // (수정,추가_백엔드) 선택한 일자의 요일 표시 (초기화)
        val datePicker:DatePicker = findViewById<DatePicker>(R.id.datepickerDate)
        val dateOfday:TextView = findViewById<TextView>(R.id.foodwirte_dateOfday)
        val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val sdfAf:SimpleDateFormat = SimpleDateFormat("E요일")
        var dateInit:String = "${datePicker.year}-${datePicker.month+1}-${datePicker.dayOfMonth}"
        dateOfday.text = sdfAf.format(sdf.parse(dateInit))


        // (수정,추가_백엔드) 변화된 일자의 요일 표시
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener { datePicker, i, i2, i3 ->
                //val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                //val sdfAf:SimpleDateFormat = SimpleDateFormat("E요일")
                var date:String = "${i}-${i2+1}-${i3}"
                dateOfday.text = sdfAf.format(sdf.parse(date))
            }
        }else{
            dateOfday.visibility = View.GONE
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
        navigationView = findViewById(R.id.nav_Food_Write)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

    }

    // (수정,추가_백엔드) 카메라 촬영
    fun callCamera(){
        if(checkPermission(FoodSingleton.getInstance().CAMERA, FoodSingleton.getInstance().CAMERA_CODE)
            && checkPermission(FoodSingleton.getInstance().STORAGE, FoodSingleton.getInstance().STORAGE_CODE)){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // startActivityForResult x
            getResult.launch(intent)
        }
    }

    // (수정,추가_백엔드) 갤러리 열기
    fun openGallery(){
        if(checkPermission(FoodSingleton.getInstance().STORAGE, FoodSingleton.getInstance().STORAGE_CODE)){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getResult.launch(intent)
        }
    }

    // (수정,추가_백엔드) 사진 저장
    fun savePicture(filename:String, mimeType:String, bitmap: Bitmap): Uri?{
        var CV = ContentValues()

        CV.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        CV.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CV.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        // MediaStore에 파일 저장
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV)
        if(uri != null){
            var scriptor = contentResolver.openFileDescriptor(uri, "w")
            val fileOS = FileOutputStream(scriptor?.fileDescriptor)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOS)
            fileOS.close()
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                CV.clear()
                CV.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri,CV,null,null)
            }
        }
        return uri
    }

    // (수정,추가_백엔드) 파일명을 날짜로 저장
    fun randomPictureName(): String{
        val filename = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        return filename
    }

    // (수정,추가_백엔드) 권한 체크
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

    // (수정,추가_백엔드) 사진 업로드
    fun uploadImage(uri:Uri, foodMenu:TextView, date:String){
        val fullPath = makeFilePath("foods",MemberDao.user!!.code!!, uri, date)
        val imageRef = FoodSingleton.getInstance().storage.getReference(fullPath)
        val uploadTask = imageRef.putFile(uri)
        var addItem:FoodDto
        var toast:Toast ?= null
        val foodWriteBtn:Button = findViewById<Button>(R.id.btnFoodWriteAf)
        val foodImg:ImageView = findViewById<ImageView>(R.id.foodactivity_foodImg)

        var imageUri:String ?= null
        uploadTask.addOnSuccessListener {
            val downloadImageRef = FoodSingleton.getInstance().storage.getReference(fullPath)
            downloadImageRef.downloadUrl.addOnSuccessListener {
                imageUri = it.toString()
                if(imageUri != null){
                    addItem = FoodDto(MemberDao.user!!.code, date, foodMenu.text.toString(), imageUri)
                    FoodSingleton.getInstance().foodItemAdd(addItem)
                    if(toast != null) toast!!.cancel()
                    toast = Toast.makeText(this,"추가 완료!", Toast.LENGTH_SHORT)
                    toast!!.show()
                    foodMenu.text = null
                    foodImg.setImageBitmap(null)
                }else{
                    if(toast != null) toast!!.cancel()
                    toast = Toast.makeText(this,"다시 등록해주세요!", Toast.LENGTH_SHORT)
                    toast!!.show()
                }
                foodWriteBtn.isEnabled = true
            }
        }.addOnFailureListener {
        }
    }

    // (수정,추가_백엔드) 저장파일 주소 설정
    fun makeFilePath(path:String, hospitalCode:String, uri:Uri, date:String):String{
        val mimeType = contentResolver.getType(uri)?:"/none"
        val ext = mimeType.split("/")[1]
        val timeSuffix = System.currentTimeMillis()
        val filename = "${path}/${hospitalCode}_${date}_${timeSuffix}.${ext}"
        // 경로/병원코드_식단날짜_작성일.확장자
        return  filename
    }

    // (수정,추가_백엔드) 요청 권한
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var toast:Toast ?= null
        when(requestCode){
            FoodSingleton.getInstance().CAMERA_CODE -> {
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        if(toast != null) toast!!.cancel()
                        toast = Toast.makeText(this,"카메라 권한 승인필요!", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            }
            FoodSingleton.getInstance().STORAGE_CODE -> {
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        if(toast != null) toast!!.cancel()
                        toast = Toast.makeText(this,"저장소 권한 승인필요!", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val loginId = findViewById<TextView>(R.id.hamLoginId)
        val mid = MemberDao.user!!.name
        loginId.text =mid.toString()+" 님"
        val loginCode = findViewById<TextView>(R.id.hamLoginCode)
        loginCode.text = "환영합니다"
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
            R.id.menu_food-> {
                val i = Intent(this, FoodActivity::class.java)
                startActivity(i)
            }
            R.id.menu_logout-> {
                AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                        val i  = Intent(this, SigninActivity::class.java)
                        val dto = MemberDto("", "", "","","","",0,0,0,0)
                        MemberDao.user = dto
                        startActivity(i)
                    })
                    .setNegativeButton("아니요", null)
                    .create()
                    .show()
            }
        }
        return false
    }
}