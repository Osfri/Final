package com.example.android.lunch

import android.os.Parcel
import android.os.Parcelable

class FoodDto(val code:String?, val fdate:String?, val menu:String?) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(fdate)
        parcel.writeString(menu)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodDto> {
        override fun createFromParcel(parcel: Parcel): FoodDto {
            return FoodDto(parcel)
        }

        override fun newArray(size: Int): Array<FoodDto?> {
            return arrayOfNulls(size)
        }
    }
}
