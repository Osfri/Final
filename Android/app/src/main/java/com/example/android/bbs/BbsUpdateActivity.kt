package com.example.android.bbs

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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.alram.AlarmActivity
import com.example.android.calendar.CalendarActivity
import com.example.android.chat.ChatActivity
import com.example.android.lunch.FoodSingleton
import com.example.android.manager.BoardtypeDto
import com.example.android.manager.ManagerActivity
import com.example.android.manager.ManagerMenuActivity
import com.example.android.offday.OffDayActivity
import com.example.android.phoneNumber.PhoneNumActivity
import com.example.android.pointMall.PointMallActivity
import com.example.android.signin.MemberDao
import com.google.android.material.navigation.NavigationView
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import android.Manifest
import android.content.DialogInterface
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.android.MainActivity
import com.example.android.signin.MemberDto
import com.example.android.signin.SigninActivity
import java.time.LocalDate

class BbsUpdateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onBackPressed() {
        val i = Intent(this,BbsDetail::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 97
    val STORAGE_CODE = 96

    companion object{
        var updateImgAddr = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbs_update)

        val data = intent.getParcelableExtra<BbsDto>("updatedata")
        val bbstype = intent?.getParcelableExtra<BoardtypeDto>("dataType")

        val updateBtn = findViewById<Button>(R.id.btn_bbsUpdateFin)
        val updateTitle = findViewById<EditText>(R.id.bbsUpdateTitle)
        val updateContent = findViewById<EditText>(R.id.bbsUpdateContent)
        val updateText = findViewById<TextView>(R.id.bbsUpdateId)
        val updateImage = findViewById<ImageView>(R.id.bbsUpdateImage)
        val camera = findViewById<ImageButton>(R.id.bbsUpdateCamera)
        camera.setOnClickListener {
            CallCamera()
        }
        // ?????? ??????
        val picture = findViewById<ImageButton>(R.id.bbsUpdateBtnImage)
        picture.setOnClickListener {
            GetAlbum()
        }

        if (data?.image == "" || data?.image == null || data?.image == " "){
            //updateImage.isGone
        }else{
            Glide.with(this)
                .load(BbsDetail.Detaildata?.image) // ????????? ????????? url
                .into(updateImage) // ???????????? ?????? ???
        }

        var title = ""
        var content = ""
        val typename = if (BbsActivity.typename == ""){"????????????"}else{BbsActivity.typename}
        updateText.text = "?????????: ${MemberDao.user!!.id} ?????????:${typename}"
        if (data?.image != null){
            updateImgAddr = data?.image!!
        }
        val headtext = findViewById<TextView>(R.id.navi_title_center)
        headtext.text = typename


        updateTitle.setText("${data?.title}")
        updateContent.setText("${data?.content}")

        updateTitle.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){title = updateTitle.text.toString()}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                title = updateTitle.text.toString()
            }
        })
        updateContent.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){title = updateTitle.text.toString()}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                content = updateContent.text.toString()
            }
        })

        updateBtn.setOnClickListener {
            val dto = BbsDto(data!!.seq,MemberDao.user?.id,title,content,0,data.wdate,0,data.type,data.code,0,data.gr,
                updateImgAddr)
            BbsDao.getInstance().updateBbs(dto)

            val bbspage = Intent(this,BbsActivity::class.java)
            bbspage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(bbspage)
        }





        //=============================================Front===========================================================
        // drawerlayout bar ??????
        val toolbar= findViewById<Toolbar>(R.id.toolbar) // toolBar??? ?????? App Bar ??????
        setSupportActionBar(toolbar) // ?????? ??????
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // ???????????? ?????? ??? ?????? ?????????
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hambar) // ????????? ????????? ??????
        supportActionBar?.setDisplayShowTitleEnabled(false) // ????????? ????????? ????????????

        // ??????????????? ????????? ??????
        drawerLayout = findViewById(R.id.drawer_layout)

        // ??????????????? ????????? ???????????? ????????? ???????????? ???????????? ?????? ??????
        navigationView = findViewById(R.id.nav_Bbs_Update)
        navigationView.setNavigationItemSelectedListener(this) //naviga









    }



    // ??????????????????
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
    // ??????????????????
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
    //================================????????????===================================================
    // ?????? ???????????? ????????? ???????????????
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

    // ????????? ?????? - ?????? ??????
    fun CallCamera(){
        if(checkPermission(CAMERA, CAMERA_CODE) && checkPermission(STORAGE, STORAGE_CODE)){
            val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(itt, CAMERA_CODE)
        }
    }

    // ?????? ??????
    fun saveFile(fileName:String, mimeType:String, bitmap: Bitmap): Uri?{

        var CV = ContentValues()

        // MediaStore ??? ?????????, mimeType ??? ??????
        CV.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        // ????????? ??????
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CV.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        // MediaStore ??? ????????? ??????
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV)
        if(uri != null){
            var scriptor = contentResolver.openFileDescriptor(uri, "w")

            val fos = FileOutputStream(scriptor?.fileDescriptor)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                CV.clear()
                // IS_PENDING ??? ?????????
                CV.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, CV, null, null)
            }
        }
        return uri
    }

    // ??????
    @SuppressLint("WrongViewCast")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = findViewById<ImageView>(R.id.bbsUpdateImage)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                //????????? ?????? ?????????
                CAMERA_CODE -> {
                    if(data?.extras?.get("data") != null){
                        val img = data?.extras?.get("data") as Bitmap
                        val uri = saveFile(RandomFileName(), "image/jpeg", img)
                        val filename:String = "bbs/${RandomFileName()}.${(contentResolver.getType(uri!!)?:"/none").split("/")[1]}"
                        val imageRef = FoodSingleton.getInstance().storage.getReference(filename)
                        val uploadTask = imageRef.putFile(uri!!)
                        uploadTask.addOnSuccessListener {
                            val downloadImageRef = FoodSingleton.getInstance().storage.getReference(filename)
                            downloadImageRef.downloadUrl.addOnSuccessListener {
                                val imageUri = it.toString()
                                updateImgAddr = imageUri
                            }
                        }.addOnFailureListener {
                        }
                        imageView.setImageURI(uri)
                        println("???????????????:$uri")
                        println("?????? ???????????????      :" + getPath(uri))
                        updateImgAddr = uri.toString()
                    }
                }
                STORAGE_CODE -> {
                    val uri = data?.data
                    imageView.setImageURI(uri)
                    updateImgAddr = uri.toString()
                }
            }
        }
    }

    // ???????????? ?????? ??????
    fun RandomFileName() : String{
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        // val path:String = "bbs/${fileName}."
        return fileName
    }

    // ????????? ??????
    fun GetAlbum(){
        if(checkPermission(STORAGE, STORAGE_CODE)){
            val itt = Intent(Intent.ACTION_PICK)
            itt.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(itt, STORAGE_CODE)
        }
    }

    //uri ???????????? ?????? ??????????????? ??????????????? > ?????? ?????? ????????? ???????????? ??????
    fun getPath(uri: Uri?): String {
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }
}