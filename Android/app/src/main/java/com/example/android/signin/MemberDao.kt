package com.example.android.signin

import com.example.android.alram.AlarmDto
import com.example.android.bbs.RetrofitClient
import com.example.android.signinAf.HospitalDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface MemberService{
    @POST("/login")
    fun login(@Body dto: MemberDto):Call<MemberDto>
    @POST("/register")
    fun register(@Body dto:MemberDto):Call<Int>
    @POST("/emailCheck")
    fun emailCheck(@Body dto: MemberDto):Call<String>
    @POST("/idCheck")
    fun idCheck(@Body dto: MemberDto): Call<String>
    @POST("/codeCheck")
    fun codeCheck(@Body code:String) : Call<HospitalDto>
    @POST("/insertHospital")
    fun insertHospital(@Body dto:HospitalDto) : Call<Int>
    @POST("/insertHospitalAf")
    fun insertHospitalAf(@Body dto:MemberDto) : Call<Int>
    @POST("/alarmList")
    fun alarmList(@Body id:String) : Call<List<AlarmDto>>
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

    fun register(dto:MemberDto) : Int?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.register(dto)
            val response = call?.execute()
            return response?.body() as Int
        } catch (e: Exception) {
            return null
        }
    }
    fun emailCheck(dto:MemberDto) : String?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.emailCheck(dto)
            val response = call?.execute()
            return response?.body() as String
        } catch (e: Exception) {
            return null
        }
    }
    fun idCheck(dto: MemberDto) : String?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.idCheck(dto)
            val response = call?.execute()
            return response?.body() as String
        } catch (e: Exception) {
            return null
        }
    }
    fun codeCheck(code:String) : HospitalDto?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.codeCheck(code)
            val response = call?.execute()
            return response?.body() as HospitalDto
        } catch (e: Exception) {
            return null
        }
    }
    fun insertHospital(dto:HospitalDto) : Int?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.insertHospital(dto)
            val response = call?.execute()
            return response?.body() as Int
        } catch (e: Exception) {
            return null
        }
    }
    fun insertHospitalAf(dto:MemberDto) : Int?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.insertHospitalAf(dto)
            val response = call?.execute()
            return response?.body() as Int
        } catch (e: Exception) {
            return null
        }
    }
    fun alarmList(id: String) : List<AlarmDto>?{
        try{
            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(MemberService::class.java)
            val call = service?.alarmList(id)
            val response = call?.execute()
            return response?.body() as List<AlarmDto>
        } catch (e: Exception) {
            return null
        }
    }
}