package com.example.android.lunch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterFood(val context: Context, val dataList:ArrayList<FoodDto>):RecyclerView.Adapter<CustomAdapterFood.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val foodRecycleViewMenu = itemView.findViewById<TextView>(R.id.foodRecycleViewMenu)        // 메뉴
        val foodRecycleViewDate = itemView.findViewById<TextView>(R.id.foodRecycleViewDate)        // 날짜

        fun bind(dataVo: FoodDto, context: Context){

            foodRecycleViewMenu.text = dataVo.menu
            foodRecycleViewDate.text = dataVo.fdate


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