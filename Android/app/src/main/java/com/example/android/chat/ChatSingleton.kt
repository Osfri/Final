package com.example.android.chat

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


// 유저 정보
class ChatUserDto{
    val id:String=""
    val name:String=""
    val email:String=""
    val pw:String=""
    val phonenumber:String=""
    val code:String=""
    val auth:Int=0
    val alarm:Int=0
    val alarmtime:Int=0
    val point:Int=0
    constructor()
    constructor(id:String):this(){}
    constructor(id:String, name:String, email:String, pw:String,
                phonenumber:String, code:String, auth:Int, alarm:Int, alarmtime:Int, point:Int):this(){}

    class HospitalDto (val name:String, val location:String, val code:String)
}

interface ChatService{
    // (0) 테스트용 (모든 유저정보 불러오기)
    @POST("getAllUserInfoApp")
    fun getAllUserInfo(): Call<List<ChatUserDto>>

    // (1) 로그인한 유저와 같은 회사코드의 회원 불러오기
    @POST("getSameCodeUsersApp")
    fun getSameCodeUsers(@Body dto:ChatUserDto): Call<MutableMap<String, ChatUserDto>>

    // (2) 로그인한 유저의 정보 얻기
    @POST("getLoginUserInfoApp")
    fun getLoginUserInfo(@Body id:String): Call<ChatUserDto>

    // (3) 로그인한 유저의 회사 정보 얻기
    @POST("getLoginUserHospitalInfoApp")
    fun getLoginUserHospitalInfo(@Body dto:ChatUserDto): Call<ChatUserDto.HospitalDto>
}
class ChatSingleton {
    // 로그인 유저정보 설정
    var loginUserId:String =""
    lateinit var loginUserInfo:ChatUserDto

    // 로그인 유저정보의 친구목록
    lateinit var peopleList: MutableMap<String, ChatUserDto>

    // 파이어베이스 설정
    val database = Firebase.database("https://finalprojectchat-7cc05-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("chatrooms")

    companion object{
        var chatSingleton: ChatSingleton ?= null

        fun getInstance(): ChatSingleton{
            if(chatSingleton == null) chatSingleton = ChatSingleton()
            return chatSingleton!!
        }
    }

    // (0) 테스트용 (모든 유저정보 불러오기)
    fun getAllUserInfo(): List<ChatUserDto>{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ChatService::class.java)
        val call = service?.getAllUserInfo()
        val response = call?.execute()
        return response?.body() as List<ChatUserDto>
    }

    fun getChatPeopleList(): MutableMap<String, ChatUserDto>{
        getSameCodeUsers(loginUserInfo)
        return peopleList
    }

    // (1) 로그인한 유저와 같은 회사코드의 회원 불러오기
    fun getSameCodeUsers(dto:ChatUserDto){
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ChatService::class.java)
        val call = service?.getSameCodeUsers(dto)
        val response = call?.execute()
        peopleList = response?.body() as MutableMap<String, ChatUserDto>
    }

    // 로그인한 유저의 정보 생성
    fun createLoginUserInfo(id:String){
        loginUserId = id
        loginUserInfo = getLoginUserInfo(loginUserId)
    }

    // (2) 로그인한 유저의 정보 얻기
    fun getLoginUserInfo(id: String): ChatUserDto{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ChatService::class.java)
        val call = service?.getLoginUserInfo(id)
        val response = call?.execute()
        return response?.body() as ChatUserDto
    }

    // (3) 로그인한 유저의 회사 정보 얻기
    fun getLoginUserHospitalInfo(dto:ChatUserDto): ChatUserDto.HospitalDto {
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ChatService::class.java)
        val call = service?.getLoginUserHospitalInfo(dto)
        val response = call?.execute()
        return response?.body() as ChatUserDto.HospitalDto
    }
}