package com.example.android.signin

import com.example.android.bbs.RetrofitClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService{
    @POST("/login")
    fun login(@Body dto: MemberDto):Call<MemberDto>
}
class MemberDao {
    companion object{
        var memberDao:MemberDao? = null
        var user:MemberDto? = null

        fun getInstance(): MemberDao{
            if(memberDao == null){
                memberDao = MemberDao()
            }
            return memberDao!!
        }
    }
    fun login(dto:MemberDto): MemberDto?{

        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.login(dto)
            val response = call?.execute()
            return response?.body() as MemberDto
        } catch (e: Exception) {
            return null
        }
    }
}