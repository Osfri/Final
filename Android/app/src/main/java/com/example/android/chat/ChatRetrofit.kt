package com.example.android.chat

import android.os.StrictMode
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ChatRetrofit {
    // Retrofit
    companion object{
        private var instance: Retrofit?= null

        fun getInstance(): Retrofit? {
            if(instance == null){
                // 외부로부터 인터넷접속해서 네트워크처리 (안드로이드 3.0이상) (Network 처리에 추가 == HttpURLConnection)
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                // Gson(Google Json) = Java객체를 JSON으로 변환
                val gson = GsonBuilder().setLenient().create()
                instance = Retrofit.Builder()
                    .baseUrl("http://119.198.55.214:3000/") // 개인아이피 추가 //172.30.1.36
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
            }
            return instance
        }
    }
}