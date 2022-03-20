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


        val bbsRecycleViewId = itemView.findViewById<TextView>(R.id.calRecycleViewId)            // 아이디
        val bbsRecycleViewTitle = itemView.findViewById<TextView>(R.id.bbsRecycleViewDate)      // 제목
        val bbsRecycleViewCount = itemView.findViewById<TextView>(R.id.calRecycleViewMemo)      // 조회수

        fun bind(dataVo:BbsDto, context: Context){
            bbsRecycleViewId.text = "2223333"
            bbsRecycleViewTitle.text = "2332"
            bbsRecycleViewCount.text = "6767"
            if(dataVo.del == 0) {
                bbsRecycleViewId.text = dataVo.id
                bbsRecycleViewTitle.text = dataVo.title
                bbsRecycleViewCount.text = dataVo.readCount.toString()
            } else{
                bbsRecycleViewId.text = ""
                bbsRecycleViewTitle.text = ""
                bbsRecycleViewCount.text = "삭제된 게시글입니다"

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
        TODO("Not yet implemented")
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_bbs, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapterBbsList.ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return dataList.size
    }


}