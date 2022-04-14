package com.example.android.pointMall

import com.example.android.chat.ChatRetrofit
import com.example.android.signin.MemberDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


// 상품정보
class ShopDto{
    var seq:Int = 0
    var title:String = ""
    var content:String = ""
    var price:Int = 0
    var photo:String = ""
    var cnt:Int = 0

    constructor()
    constructor(seq:Int):super(){
        this.seq = seq
    }
    constructor(seq:Int, title:String, content:String, price:Int, photo:String, cnt:Int):super(){
        this.seq = seq
        this.title = title
        this.content = content
        this.price = price
        this.photo = photo
        this.cnt = cnt
    }

}

interface ShopService{
    // (2) 모든 상품가져오기
    @POST("getShopItemAllApp")
    fun getShopItemAll(@Body code:String): Call<List<ShopDto>>

    // (3) 선택한 상품정보 가져오기
    @POST("getShopItemInfoApp")
    fun getShopItemInfo(@Body dto: ShopDto): Call<ShopDto>

    // (4) 상품 구매하기
    @POST("buyShopItemPointApp")
    fun buyShopItemPoint(@Body map:MutableMap<String, Any>): Call<Int>

    // (5) 구매한 상품 주문내역에 추가하기
    @POST("orderShopItemApp")
    fun orderShopItem(@Body map:MutableMap<String, Any>): Call<Int>

    // (6) 구매한 상품 재고변경
    @POST("buyShopItemCntApp")
    fun buyShopItemCnt(@Body map:MutableMap<String, Any>): Call<Int>

}

class PointMallSingleton {
    // 상품아이템 리스트
    lateinit var shopItemList: MutableList<ShopDto>

    companion object{
        var pintMallSingleton:PointMallSingleton ?= null

        // 싱글톤 적용
        fun getInstance(): PointMallSingleton{
            if(pintMallSingleton == null) pintMallSingleton = PointMallSingleton()
            return  pintMallSingleton!!
        }
    }

    // retrofit은 chat에서 끌어다 사용
    // (2) 모든 상품가져오기
    fun getShopItemAll(code:String){
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ShopService::class.java)
        val call = service?.getShopItemAll(code)
        val response = call?.execute()
        shopItemList = response?.body() as MutableList<ShopDto>
    }

    // (3) 선택한 상품정보 가져오기
    fun getShopItemInfo(dto: ShopDto): ShopDto{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ShopService::class.java)
        val call = service?.getShopItemInfo(dto)
        val response = call?.execute()
        return response?.body() as ShopDto
    }

    // (4) 상품 구매하기
    fun buyShopItemPoint(mem:MemberDto, item:ShopDto, itemCnt:Int): Int{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ShopService::class.java)
        val map:MutableMap<String, Any> = mutableMapOf()
        map.put("loginUser", mem)
        map.put("shopItem", item)
        map.put("itemCnt", itemCnt)
        val call = service?.buyShopItemPoint(map)
        val response = call?.execute()
        return response?.body() as Int
    }

    // (5) 구매한 상품 주문내역에 추가하기
    fun orderShopItem(mem:MemberDto, item:ShopDto, itemCnt:Int): Int{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ShopService::class.java)
        val map:MutableMap<String, Any> = mutableMapOf()
        map.put("loginUser", mem)
        map.put("shopItem", item)
        map.put("itemCnt", itemCnt)
        val call = service?.orderShopItem(map)
        val response = call?.execute()
        return response?.body() as Int
    }

    // (6) 구매한 상품 재고변경
    fun buyShopItemCnt(item:ShopDto, itemCnt:Int): Int{
        val retrofit = ChatRetrofit.getInstance()
        val service = retrofit?.create(ShopService::class.java)
        val map:MutableMap<String, Any> = mutableMapOf()
        map.put("shopItem", item)
        map.put("itemCnt", itemCnt)
        val call = service?.buyShopItemCnt(map)
        val response = call?.execute()
        return response?.body() as Int
    }
}