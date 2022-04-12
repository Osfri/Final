package com.example.android.offday

class OffdayDto {
    var id:String = ""
    var wdate:Any =""
    var content:MutableList<OffdayDto> = mutableListOf()
    var time:String = ""
    var name:String = ""


    constructor(wdate:Any):super(){
        this.wdate = wdate
    }
    constructor(wdate:Any, id:String):super(){
        this.wdate = wdate
        this.id = id
    }
    constructor(wdate: Any, content:MutableList<OffdayDto>):super(){
        this.wdate = wdate
        this.content = content
    }
    constructor(id: String, wdate: String, time:String):super(){
        this.id = id
        this.wdate = wdate
        this.time = time
    }
    constructor(id: String, wdate: String, time:String, name:String):super(){
        this.id = id
        this.wdate = wdate
        this.time = time
        this.name = name
    }

    override fun toString(): String {
        return "OffdayDto(id='$id', wdate=$wdate, content=$content, time='$time', name='$name')"
    }

}