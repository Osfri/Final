package com.example.android.bbs

import retrofit2.Call
import retrofit2.http.GET

interface BbsService {

    @GET("/getBbsList")
    fun getBbsList(code:String): Call<List<BbsDto>>

    @GET("/bbswrite")
    fun bbswrite(dto:BbsDto) : Call<String>
}


class BbsDao {

    companion object{
        var bbsDao:BbsDao? = null

        fun getInstance():BbsDao{
            if(bbsDao == null){
                bbsDao = BbsDao()
            }
            return bbsDao!!
    }
}

fun getBbsList(code:String):ArrayList<BbsDto>{
    val retrofit = RetrofitClient.getInstance()

    val service = retrofit?.create(BbsService::class.java)
    val call = service?.getBbsList(code)
    val response = call?.execute()

    return response?.body() as ArrayList<BbsDto>
}

fun bbswrite(bbs:BbsDto) :String {
    val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbswrite(bbs)
        val response = call?.execute()

        return response?.body() as String
    }


}
