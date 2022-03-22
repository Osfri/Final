package com.example.android.phoneNumber

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.bbs.BbsDto
import com.example.android.pointMall.CustomAdapterPointMall
import com.example.android.pointMall.PointMallDto

class CustomAdapterPhone():RecyclerView.Adapter<CustomAdapterPhone.ItemViewHolder>()  {
    var name = arrayOf("one", "two", "three", "four", "five")
    var phoneNum = arrayOf( "010-123-4567","010-123-4567","010-123-4567","010-123-4567","010-123-4567")

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val phoneRecycleViewNumber = itemView.findViewById<TextView>(R.id.phoneRecycleViewNumber)            // 연락처 번호
        val phoneRecycleViewName = itemView.findViewById<TextView>(R.id.phoneRecycleViewName)      // 연락처 이름

        fun bind(dataVo: PhoneNumDto, context: Context){

            phoneRecycleViewName.text=dataVo.name
            phoneRecycleViewNumber.text = dataVo.phoneNum

        }
    }
    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewgroup.context).inflate(R.layout.view_item_layout_phone, viewgroup,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder:ItemViewHolder, position: Int) {
        holder.phoneRecycleViewName.setText(name.get(position))
        holder.phoneRecycleViewNumber.setText(phoneNum.get(position))
    }

    override fun getItemCount(): Int {
        return name.size
    }
}