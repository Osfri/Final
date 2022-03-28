package com.example.android.calendar

import android.os.Parcel
import android.os.Parcelable
import com.example.android.lunch.FoodDto

class CalendarDto {
    var date:Any =""
    var content:MutableList<String> = mutableListOf()


    constructor(date:Any):super(){
        this.date = date
    }
    constructor(date: Any, content:MutableList<String>):super(){
        this.date = date
        this.content = content
    }
}