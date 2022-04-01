package com.example.android.pointMall

import android.os.Parcel
import android.os.Parcelable
import com.example.android.bbs.BbsDto
import com.example.android.lunch.FoodDto

class PointMallDto(val seq:Int, val title:String?, val content:String?, val image:String?) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(seq)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(image)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PointMallDto> {
        override fun createFromParcel(parcel: Parcel): PointMallDto {
            return PointMallDto(parcel)
        }

        override fun newArray(size: Int): Array<PointMallDto?> {
            return arrayOfNulls(size)
        }
    }
}

