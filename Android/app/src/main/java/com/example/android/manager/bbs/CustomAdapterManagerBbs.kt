package com.example.android.manager.bbs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterManagerBbs(val context: Context, val dataList:ArrayList<ManagerBbsDto>):
    RecyclerView.Adapter<CustomAdapterManagerBbs.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val managerRecycleViewBbsTitle = itemView.findViewById<TextView>(R.id.managerRecycleViewBbsTitle)        // 메뉴
        val managerRecycleViewBbsId = itemView.findViewById<TextView>(R.id.managerRecycleViewBbsId)        // 날짜

        fun bind(dataVo: ManagerBbsDto, context: Context){

            managerRecycleViewBbsTitle.text = dataVo.title
            managerRecycleViewBbsId.text = dataVo.id


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_manager_bbs, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)

    }

    override fun getItemCount(): Int {

        return dataList.size
    }


}