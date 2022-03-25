package com.example.android.offday

import com.example.android.bbs.RetrofitClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OffDayService{
    @POST("/offApply")
    fun offApply(@Body offlist: MutableList<OffdayDto>): Call<String>
    @POST("/offList")
    fun offList(@Body date: String): Call<List<OffdayDto>>
}

class OffDayDao {
    companion object{
        var offdayDao: OffDayDao? = null
        var useroff: MutableList<OffdayDto>? = null

        fun getInstance(): OffDayDao {
            if(offdayDao == null){
                offdayDao = OffDayDao()
            }
            return offdayDao!!
        }
    }

    fun offApply(offlist: MutableList<OffdayDto>) : String?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(OffDayService::class.java)
            val call = service?.offApply(offlist)
            val response = call?.execute()
            return response?.body() as String
        } catch (e: Exception) {
            return null
        }
    }

    fun offList(date: String): List<OffdayDto>?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(OffDayService::class.java)
            val call = service?.offList(date)
            val response = call?.execute()
            return response?.body() as List<OffdayDto>
        } catch (e: Exception) {
            return null
        }
    }
}