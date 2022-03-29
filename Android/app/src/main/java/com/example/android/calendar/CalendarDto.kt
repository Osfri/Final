package com.example.android.calendar

import android.os.Parcel
import android.os.Parcelable
import com.example.android.lunch.FoodDto

class CalendarDto {
    var wdate:Any =""
    var time:String = ""
    var content:MutableList<String> = mutableListOf()


    constructor(wdate:Any):super(){
        this.wdate = wdate
    }
    constructor(wdate: Any, content:MutableList<String>):super(){
        this.wdate = wdate
        this.content = content
    }
    constructor(wdate: Any, time:String):super(){
        this.wdate = wdate
        this.time = time
    }
}