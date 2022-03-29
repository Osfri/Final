package com.example.android.bbs

import android.os.Parcel
import android.os.Parcelable

class BbsDto (val seq:Int,
              val id:String?,
              val title:String?,
              val content:String?,
              val readCount:Int,
              val wdate:String?,
              val del:Int,
              val type:Int,
              val code:String?,
              val step:Int) : Parcelable {

    constructor(parcel:Parcel):this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(seq)
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(readCount)
        parcel.writeString(wdate)
        parcel.writeInt(del)
        parcel.writeInt(type)
        parcel.writeString(code)
        parcel.writeInt(step)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BbsDto> {
        override fun createFromParcel(parcel: Parcel): BbsDto {
            return BbsDto(parcel)
        }

        override fun newArray(size: Int): Array<BbsDto?> {
            return arrayOfNulls(size)
        }
    }


}