package com.example.android.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterCal(val context: Context, val dataList:ArrayList<CalendarDto>):RecyclerView.Adapter<CustomAdapterCal.ItemViewHolder>(){


    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val calRecycleViewId = itemView.findViewById<TextView>(R.id.calRecycleViewId)        // 메뉴
        val calRecycleViewMemo = itemView.findViewById<TextView>(R.id.calRecycleViewMemo)      // 메모
        val calRecycleViewTime = itemView.findViewById<TextView>(R.id.calRecycleViewTime)      // 파트타임 구분
        val calRecycleViewDate = itemView.findViewById<TextView>(R.id.calRecycleViewDate)      // 날짜


        fun bind(dataVo: CalendarDto, context: Context){

            calRecycleViewId.text = dataVo.id
            calRecycleViewMemo.text = dataVo.memo
            calRecycleViewTime.text = dataVo.time
            calRecycleViewDate.text = dataVo.wdate



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_calendar, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}