package com.example.android.phoneNumber

import android.os.Parcel
import android.os.Parcelable
import com.example.android.bbs.BbsDto
import com.example.android.lunch.FoodDto

class PhoneNumDto(val seq:Int, val name:String?, val phoneNum:String?) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(seq)
        parcel.writeString(name)
        parcel.writeString(phoneNum)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhoneNumDto> {
        override fun createFromParcel(parcel: Parcel): PhoneNumDto {
            return PhoneNumDto(parcel)
        }

        override fun newArray(size: Int): Array<PhoneNumDto?> {
            return arrayOfNulls(size)
        }
    }
}

