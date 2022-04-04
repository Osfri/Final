package com.example.android.manager

import android.os.Parcel
import android.os.Parcelable

class BoardtypeDto(val type:Int,val name:String?,val code:String?,val auth:Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(type)
        parcel.writeString(name)
        parcel.writeString(code)
        parcel.writeInt(auth)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "BoardtypeDto(type=$type, name=$name, code=$code, auth=$auth)"
    }

    companion object CREATOR : Parcelable.Creator<BoardtypeDto> {
        override fun createFromParcel(parcel: Parcel): BoardtypeDto {
            return BoardtypeDto(parcel)
        }

        override fun newArray(size: Int): Array<BoardtypeDto?> {
            return arrayOfNulls(size)
        }
    }


}