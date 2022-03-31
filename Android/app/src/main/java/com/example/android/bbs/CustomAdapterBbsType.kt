package com.example.android.bbs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.manager.BoardtypeDto

class CustomAdapterBbsType(val context: Context, val dataList:ArrayList<BoardtypeDto>): RecyclerView.Adapter<CustomAdapterBbsType.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {


        val bbsTypeRecycleViewName = itemView.findViewById<TextView>(R.id.bbsTypeRecycleViewId)


        fun bind(dataVo:BoardtypeDto, context: Context){

            bbsTypeRecycleViewName.text = dataVo.type.toString()

            itemView.setOnClickListener{

                    Intent(context, BbsActivity::class.java).apply {
                        putExtra("data", dataVo)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run {
                        context.startActivity(this)
                 }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterBbsType.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_bbstype, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapterBbsType.ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

