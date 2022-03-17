package com.example.android.signin

import android.os.Parcel
import android.os.Parcelable


//id, name , email , pw , phonenumber , code , auth , alarm , alarmtime , point
class MemberDto(val id:String? , val name:String? , val email:String? , val pw:String? , val phonenumber:String? , val code:String?,
                val auth:Int? , val alarm:Int? , val alarmtime:Int, val point:Int) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ){}
    override fun writeToParcel(parcel: Parcel,flags:Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(pw)
        parcel.writeString(phonenumber)
        parcel.writeString(code)
        parcel.writeValue(auth)
        parcel.writeValue(alarm)
        parcel.writeInt(alarmtime)
        parcel.writeInt(point)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "MemberDto(id=$id, name=$name, email=$email, pw=$pw, phonenumber=$phonenumber, code=$code, auth=$auth, alarm=$alarm, alarmtime=$alarmtime, point=$point)"
    }

    companion object CREATOR : Parcelable.Creator<MemberDto> {
        override fun createFromParcel(parcel: Parcel): MemberDto {
            return MemberDto(parcel)
        }

        override fun newArray(size: Int): Array<MemberDto?> {
            return arrayOfNulls(size)
        }
    }

}