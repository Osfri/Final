package com.example.android.bbs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterBbsList(val context: Context, val dataList:ArrayList<BbsDto>):RecyclerView.Adapter<CustomAdapterBbsList.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


        val bbsRecycleViewId = itemView.findViewById<TextView>(R.id.bbsRecycleViewId)            // 아이디
        val bbsRecycleViewTitle = itemView.findViewById<TextView>(R.id.bbsRecycleViewTitle)      // 제목
        val bbsRecycleViewCount = itemView.findViewById<TextView>(R.id.bbsRecycleViewCount)      // 조회수
        val bbsRecycleViewdate = itemView.findViewById<TextView>(R.id.bbsRecycleViewdate)      // 날짜

        fun bind(dataVo:BbsDto, context: Context){

            if (dataVo.del == 0) {
                bbsRecycleViewId.text = dataVo.id
                bbsRecycleViewTitle.text = dataVo.title
                bbsRecycleViewCount.text = dataVo.readCount.toString()
                bbsRecycleViewdate.text = dataVo.wdate

            } else {

                bbsRecycleViewId.text = ""
                bbsRecycleViewTitle.text = ""
                bbsRecycleViewCount.text = "삭제됨"
                bbsRecycleViewdate.text = ""
            }


            itemView.setOnClickListener{
                if(dataVo!!.del == 0) {                      //    isFocusable 터치해서 접근여부
                    Intent(context, BbsDetail::class.java).apply {

                        putExtra("data", dataVo)

                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { context.startActivity(this) }
                } else{

                }
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterBbsList.ItemViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_bbs, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapterBbsList.ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}