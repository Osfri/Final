package com.example.android.bbs

import android.os.Parcel
import android.os.Parcelable

class CommentDto (val commentseq:Int,           // 댓글 번호
                  val boardseq:Int,            // 게시글 번호
                  val ref:Int,                 //
                  val step:Int,
                  val depth:Int,
                  val content:String?,          // 댓글 내용
                  val wdate:String?,                // 댓글 날짜
                  val del:Int) : Parcelable {   // 댓글 삭제 여부 0,1

    constructor(parcel:Parcel):this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(commentseq)
        parcel.writeInt(boardseq)
        parcel.writeInt(ref)
        parcel.writeInt(step)
        parcel.writeInt(depth)
        parcel.writeString(content)
        parcel.writeString(wdate)
        parcel.writeInt(del)
    }



    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentDto> {
        override fun createFromParcel(parcel: Parcel): CommentDto {
            return CommentDto(parcel)
        }

        override fun newArray(size: Int): Array<CommentDto?> {
            return arrayOfNulls(size)
        }
    }


}