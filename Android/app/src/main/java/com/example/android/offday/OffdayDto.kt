package com.example.android.offday

class OffdayDto {
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