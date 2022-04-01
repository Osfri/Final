package com.example.android.lunch

import android.Manifest
import com.example.android.chat.ChatRetrofit
import com.example.android.pointMall.ShopService
import com.example.android.signin.MemberDto
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface FoodService{
    // (1) 로그인한 유저의 병원 식단 가져오기
    @POST("getSameCodeFoodInfoApp")
    fun getSameCodeFoodInfo(@Body dto: MemberDto): Call<List<FoodDto>>

    // (2) 로그인한 유저의 병원 식단 추가하기
    @POST("foodItemAddApp")
    fun foodItemAdd(@Body dto: FoodDto): Call<Int>

}

class FoodSingleton {

    // 식단아이템 리스트
    lateinit var foodItemList: MutableList<FoodDto>

    // storage 권한 처리에 필요한 변수
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    // 파이어베이스 설정
    val storage = Firebase.storage("gs://finalprojectchat-7cc05.appspot.com")

    companion object{
        var foodSingleton: FoodSingleton?= null

        // 싱글톤 적용
        fun getInstance(): FoodSingleton {
            if(foodSingleton == null) foodSingleton = FoodSingleton()
            return  foodSingleton!!
        }
    }

    // retrofit은 chat에서 끌어다 사용
    // (1) 로그인한 유저의 병원 식단 가져오기
    fun getSameCodeFoodInfo(dto: MemberDto){
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(FoodService::class.java)
        val call = service?.getSameCodeFoodInfo(dto)
        val response = call?.execute()
        foodItemList = response?.body() as MutableList<FoodDto>
    }

    // (2) 로그인한 유저의 병원 식단 추가하기
    fun foodItemAdd(dto: FoodDto): Int{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(FoodService::class.java)
        val call = service?.foodItemAdd(dto)
        val response = call?.execute()
        return response?.body() as Int
    }
}