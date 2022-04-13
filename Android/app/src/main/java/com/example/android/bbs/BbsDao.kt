package com.example.android.bbs

import com.example.android.manager.BoardtypeDto
import com.example.android.bbs.RetrofitClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BbsService {

    @POST("getBbsList")
    fun getBbsList(@Body map: MutableMap<String,Any>): Call<ArrayList<BbsDto>>

    @POST("bbswrite")
    fun bbswrite(@Body dto:BbsDto) : Call<Int>

    @POST("bbsAdd")
    fun bbsAdd(@Body dto:BoardtypeDto) : Call<Int>

    @POST("bbsRandomCheck")
    fun bbsRandomCheck(@Body type: Int) : Call<BoardtypeDto>

    @POST("getBoardTypeList")
    fun getBoardTypeList(@Body code: String) : Call<List<BoardtypeDto>>

    @POST("getCommentList")
    fun getCommentList(@Body gr:Int) : Call<ArrayList<BbsDto>>

    @POST("deleteBbs")
    fun deleteBbs(@Body seq: Int) : Call<Int>

    @POST("updateBbs")
    fun updateBbs(@Body dto:BbsDto) : Call<Int>

    @POST("commentwrite")
    fun commentwrite(@Body dto:BbsDto) : Call<Int>

    @POST("updatecomment")
    fun updatecomment(@Body dto: BbsDto) : Call<Int>

    @POST("deleteBoardTypeDto")
    fun deleteBoardTypeDto(@Body dto:BoardtypeDto) : Call<Int>
}


class BbsDao {

    companion object {
        var bbsDao: BbsDao? = null

        fun getInstance(): BbsDao {
            if (bbsDao == null) {
                bbsDao = BbsDao()
            }
            return bbsDao!!
        }
    }
    // 병원 코드 , 게시물 타입
    fun getBbsList(code: String?,type: Int?): ArrayList<BbsDto>? {
        println("게시물 불러오기"+code+type)
        try {
            val map = mutableMapOf<String, Any>()
            map.put("code", code!!)
            map.put("type", type!!)
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.getBbsList(map)
        val response = call?.execute()

        return response?.body() as ArrayList<BbsDto>
        }catch (e:Exception){
            return null
        }
    }
    //게시물 작성
    fun bbswrite(bbs: BbsDto): Int? {
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbswrite(bbs)
        val response = call?.execute()

        return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }

    //게시판 생성
    fun bbsAdd(dto: BoardtypeDto): Int? {
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbsAdd(dto)
        val response = call?.execute()

        return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }

    //랜덤 생성된 BoardType 의 type 중복 확인
    fun bbsRandomCheck(type: Int): BoardtypeDto? {
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbsRandomCheck(type)
        val response = call?.execute()

        return response?.body() as BoardtypeDto
        }catch (e:Exception){
            return null
        }
    }

    // 게시판 불러오기 게시물x
    fun getBoardTypeList(code: String) : ArrayList<BoardtypeDto>? {
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.getBoardTypeList(code)
        val response = call?.execute()

        return response?.body() as ArrayList<BoardtypeDto>
        }catch (e:Exception){
            return null
        }
    }

    //댓글 불러오기
    fun getCommentList(gr:Int) : ArrayList<BbsDto>?{
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.getCommentList(gr)
        val response = call?.execute()

        return response?.body() as ArrayList<BbsDto>
        }catch (e:Exception){
            return null
        }
    }

    //게시글 삭제 , 댓글도 가능
    fun deleteBbs(seq:Int) : Int? {
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.deleteBbs(seq)
        val response = call?.execute()

        return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }

    //게시물,댓글 수정
    fun updateBbs(dto:BbsDto) : Int?{
        try {

        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.updateBbs(dto)
        val response = call?.execute()

        return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }
    //댓글쓰기
    fun commentwrite(dto:BbsDto) : Int?{
        try {

            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(BbsService::class.java)
            val call = service?.commentwrite(dto)
            val response = call?.execute()

            return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }
    //댓글 수정
    fun updatecomment(dto: BbsDto) : Int?{
        try {

            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(BbsService::class.java)
            val call = service?.updatecomment(dto)
            val response = call?.execute()

            return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }
    fun deleteBoardTypeDto(dto:BoardtypeDto) : Int?{
        try {

            val retrofit = RetrofitClient.getInstance()

            val service = retrofit?.create(BbsService::class.java)
            val call = service?.deleteBoardTypeDto(dto)
            val response = call?.execute()

            return response?.body() as Int
        }catch (e:Exception){
            return null
        }
    }


}
