package com.example.android.calendar

import android.os.Parcel
import android.os.Parcelable
import com.example.android.lunch.FoodDto

class CalendarDto(val id:String?, val wdate:String?, val time:String?, val memo:String?) :Parcelable  {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(wdate)
        parcel.writeString(time)
        parcel.writeString(memo)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalendarDto> {
        override fun createFromParcel(parcel: Parcel): CalendarDto {
            return CalendarDto(parcel)
        }

        override fun newArray(size: Int): Array<CalendarDto?> {
            return arrayOfNulls(size)
        }
    }
}