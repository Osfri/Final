package com.example.android.calendar

import android.os.Parcel
import android.os.Parcelable
import com.example.android.lunch.FoodDto

class CalendarDto {
    var wdate:Any =""
    var time:String = ""
    //var content:MutableList<String> = mutableListOf()
    var content:CalendarDto? = null
    var id:String = ""

    constructor(wdate:Any):super(){
        this.wdate = wdate
    }
    constructor(wdate: Any, content:CalendarDto):super(){
        this.wdate = wdate
        this.content = content
    }
    constructor(wdate: Any, time:String, id:String):super(){
        this.wdate = wdate
        this.time = time
        this.id = id
    }

    override fun toString(): String {
        return "CalendarDto(wdate=$wdate, time='$time', content=$content, id='$id')"
    }
}