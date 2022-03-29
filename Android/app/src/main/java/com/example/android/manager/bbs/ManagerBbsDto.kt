package com.example.android.manager.bbs

import android.os.Parcel
import android.os.Parcelable

class ManagerBbsDto(val seq:Int?, val title:String?, val id:String?) : Parcelable {

    constructor(parcel:Parcel):this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()

    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(seq!!)
        parcel.writeString(id)
        parcel.writeString(title)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ManagerBbsDto> {
        override fun createFromParcel(parcel: Parcel): ManagerBbsDto {
            return ManagerBbsDto(parcel)
        }

        override fun newArray(size: Int): Array<ManagerBbsDto?> {
            return arrayOfNulls(size)
        }
    }


}