package com.example.android.alram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterAlarm(val context: Context,val dataList: List<AlarmDto>):RecyclerView.Adapter<CustomAdapterAlarm.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val alarmName = itemView.findViewById<TextView>(R.id.Alarm_name)
        val alarmStartTime = itemView.findViewById<TextView>(R.id.Alarm_starttime)
        val alarmEndTime = itemView.findViewById<TextView>(R.id.Alarm_endtime)
        val alarmText = itemView.findViewById<TextView>(R.id.Alarm_text)

        fun bind(dataVo:AlarmDto,context: Context){
            alarmName.text = dataVo.name
            alarmStartTime.text = dataVo.starttime
            alarmEndTime.text = dataVo.endtime
            alarmText.text = "~"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_alarmitem,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
