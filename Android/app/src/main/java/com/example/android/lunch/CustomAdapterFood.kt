package com.example.android.lunch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import java.text.SimpleDateFormat

// (수정,추가_백엔드) 리사이클러뷰 어댑터
class CustomAdapterFood(val context: Context, val dataList:MutableList<FoodDto>):RecyclerView.Adapter<CustomAdapterFood.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val foodRecycleViewMenu = itemView.findViewById<TextView>(R.id.foodRecycleViewMenu)        // 메뉴
        val foodRecycleViewDate = itemView.findViewById<TextView>(R.id.foodRecycleViewDate)        // 날짜
        val foodRecycleViewImg = itemView.findViewById<ImageView>(R.id.foodRecycleViewImage)        // 이미지
        val sdfAf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd E요일")
        val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        fun bind(dataVo: FoodDto, context: Context){

            foodRecycleViewMenu.text = dataVo.menu
            foodRecycleViewDate.text = sdfAf.format(sdf.parse(dataVo.fdate))
            //foodRecycleViewDate.text = sdf.format(dataVo.fdate)
            if(dataVo.photo != "" && dataVo.photo != null){
                val resourceId = context.resources.getIdentifier(dataVo.photo,"drawable",context.packageName)
                if(resourceId > 0){
                    foodRecycleViewImg.setImageResource(resourceId)
                    foodRecycleViewImg.setClipToOutline(true)
                }else{
                    foodRecycleViewImg.setBackgroundResource(0)
                    Glide.with(context).load(dataVo.photo).into(foodRecycleViewImg)
                }
            }else{
                foodRecycleViewImg.setImageResource(R.drawable.outline_dining_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_food, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)

    }

    override fun getItemCount(): Int {

        return dataList.size
    }


}