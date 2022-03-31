package com.example.android.bbs

import com.example.android.manager.BoardtypeDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface BbsService {

    @GET("/getBbsList")
    fun getBbsList(code:String,type: Int): Call<ArrayList<BbsDto>>

    @GET("/bbswrite")
    fun bbswrite(dto:BbsDto) : Call<String>

    @GET("/bbsAdd")
    fun bbsAdd(dto:BoardtypeDto) : Call<Int>

    @POST("/bbsRandomCheck")
    fun bbsRandomCheck(type: Int) : Call<BoardtypeDto>

    @GET("/getBoardTypeList")
    fun getBoardTypeList(code: String) : Call<ArrayList<BoardtypeDto>>

    @GET("/getCommentList")
    fun getCommentList(gr:Int) : Call<ArrayList<BbsDto>>

    @GET("/deleteBbs")
    fun deleteBbs(seq: Int) : Call<Int>

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
    fun getBbsList(code: String,type: Int): ArrayList<BbsDto> {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.getBbsList(code,type)
        val response = call?.execute()

        return response?.body() as ArrayList<BbsDto>
    }
    //게시물 작성
    fun bbswrite(bbs: BbsDto): String {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbswrite(bbs)
        val response = call?.execute()

        return response?.body() as String
    }

    //게시판 생성
    fun bbsAdd(dto: BoardtypeDto): Int {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbsAdd(dto)
        val response = call?.execute()

        return response?.body() as Int
    }

    //랜덤 생성된 BoardType 의 type 중복 확인
    fun bbsRandomCheck(type: Int): BoardtypeDto {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.bbsRandomCheck(type)
        val response = call?.execute()

        return response?.body() as BoardtypeDto
    }

    // 게시판 불러오기 게시물x
    fun getBoardTypeList(code: String) : ArrayList<BoardtypeDto> {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.getBoardTypeList(code)
        val response = call?.execute()

        return response?.body() as ArrayList<BoardtypeDto>
    }

    //댓글 불러오기
    fun getCommentList(gr:Int) : ArrayList<BbsDto>{
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.getCommentList(gr)
        val response = call?.execute()

        return response?.body() as ArrayList<BbsDto>
    }

    //게시글 삭제 , 댓글도 가능
    fun deleteBbs(seq:Int) : Int {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(BbsService::class.java)
        val call = service?.deleteBbs(seq)
        val response = call?.execute()

        return response?.body() as Int
    }


}
