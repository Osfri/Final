package com.example.android.calendar

import com.example.android.bbs.RetrofitClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CalendarService{
    @POST("/dutyList")
    fun dutyList(@Body id: String): Call<List<CalendarDto>>
}

class CalendarDao {
    companion object{
        var calendarDao: CalendarDao? = null

        fun getInstance(): CalendarDao {
            if(calendarDao == null){
                calendarDao = CalendarDao()
            }
            return calendarDao!!
        }
    }

    fun dutyList(id: String): List<CalendarDto>?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(CalendarService::class.java)
            val call = service?.dutyList(id)
            val response = call?.execute()
            return response?.body() as List<CalendarDto>
        } catch (e: Exception) {
            return null
        }
    }
}