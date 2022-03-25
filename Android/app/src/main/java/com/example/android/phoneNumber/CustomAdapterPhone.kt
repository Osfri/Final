package com.example.android.phoneNumber

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.bbs.BbsDto
import com.example.android.chat.ChatUserDto
import com.example.android.pointMall.CustomAdapterPointMall
import com.example.android.pointMall.PointMallDto

// (수정,추가_백엔드) 리사이클러뷰 어댑터
class CustomAdapterPhone(val context: Context, val peopleList:MutableList<ChatUserDto>)
    :RecyclerView.Adapter<CustomAdapterPhone.ItemViewHolder>()  {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val phoneRecycleViewNumber = itemView.findViewById<TextView>(R.id.phoneRecycleViewNumber)            // 연락처 번호
        val phoneRecycleViewName = itemView.findViewById<TextView>(R.id.phoneRecycleViewName)      // 연락처 이름

        fun bind(dataVO: ChatUserDto, context: Context){
            // (수정,추가_백엔드) 데이터 셋팅
            phoneRecycleViewName.text = dataVO.name
            phoneRecycleViewNumber.text = dataVO.phonenumber
        }
    }
    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewgroup.context).inflate(R.layout.view_item_layout_phone, viewgroup,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder:ItemViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(peopleList[position], context)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }
}