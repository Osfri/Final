package com.example.android.alram

import android.os.Parcel
import android.os.Parcelable

class AlarmDto(val id:String?,val name:String?,val starttime:String?,val endtime:String?,val code:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(starttime)
        parcel.writeString(endtime)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "AlarmDto(id=$id, name=$name, starttime=$starttime, endtime=$endtime, code=$code)"
    }

    companion object CREATOR : Parcelable.Creator<AlarmDto> {
        override fun createFromParcel(parcel: Parcel): AlarmDto {
            return AlarmDto(parcel)
        }

        override fun newArray(size: Int): Array<AlarmDto?> {
            return arrayOfNulls(size)
        }
    }


}