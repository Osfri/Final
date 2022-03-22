package com.example.android.signinAf

import android.os.Parcel
import android.os.Parcelable

class HospitalDto(val name:String?, val location:String?, val code:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "HospitalDto(name=$name, location=$location, code=$code)"
    }

    companion object CREATOR : Parcelable.Creator<HospitalDto> {
        override fun createFromParcel(parcel: Parcel): HospitalDto {
            return HospitalDto(parcel)
        }

        override fun newArray(size: Int): Array<HospitalDto?> {
            return arrayOfNulls(size)
        }
    }

}