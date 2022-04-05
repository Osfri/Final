package com.example.android.bbs

import android.os.StrictMode
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object {
        private var instance: Retrofit? = null

        fun getInstance(): Retrofit? {
            if(instance == null) {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                instance = Retrofit.Builder()
                    .baseUrl("http://172.30.112.64:3000/")// 자기 아이피 주소만 변경하시면 됩니다. 변경사항
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
            }
            return instance!!
        }
    }
}