package com.example.android.offday

class OffdayDto {
    var id:String = ""
    var wdate:Any =""
    var content:MutableList<String> = mutableListOf()
    var time:String = ""


    constructor(wdate:Any):super(){
        this.wdate = wdate
    }
    constructor(wdate: Any, content:MutableList<String>):super(){
        this.wdate = wdate
        this.content = content
    }
    constructor(id: String, wdate: String, time:String):super(){
        this.id = id
        this.wdate = wdate
        this.time = time
    }
}