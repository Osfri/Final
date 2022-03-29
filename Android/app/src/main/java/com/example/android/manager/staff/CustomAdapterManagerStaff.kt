package com.example.android.manager.staff

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterManagerStaff(val context: Context, val dataList:ArrayList<ManagerStaffJoinDto>):
    RecyclerView.Adapter<CustomAdapterManagerStaff.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val managerRecycleViewStaffName = itemView.findViewById<TextView>(R.id.managerRecycleViewStaffName)        // 가입승인 이름

        fun bind(dataVo: ManagerStaffJoinDto, context: Context){

            managerRecycleViewStaffName.text = dataVo.name


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_manager_staff, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)

    }

    override fun getItemCount(): Int {

        return dataList.size
    }


}