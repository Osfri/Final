package com.example.android.manager.staff

import android.os.Parcel
import android.os.Parcelable
import com.example.android.manager.bbs.ManagerBbsDto

// 가입 대기 - 승인 신청 dto
class ManagerStaffJoinDto(val name:String?, val id:String?) : Parcelable {

    constructor(parcel: Parcel):this(
        parcel.readString(),
        parcel.readString()

    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ManagerStaffJoinDto> {
        override fun createFromParcel(parcel: Parcel): ManagerStaffJoinDto {
            return ManagerStaffJoinDto(parcel)
        }

        override fun newArray(size: Int): Array<ManagerStaffJoinDto?> {
            return arrayOfNulls(size)
        }
    }


}