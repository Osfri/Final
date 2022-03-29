package com.example.android.bbs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterCommentList(val context: Context, val dataList:ArrayList<CommentDto>):RecyclerView.Adapter<CustomAdapterCommentList.ItemViewHolder>()  {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val bbsCommentRecycleViewId = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewId)            // 아이디
        val bbsCommentRecycleViewDate = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewDate)      // 날짜
        val bbsCommentRecycleViewContent = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewContent)      // 내용


        fun bind(dataVo:CommentDto, context: Context){

            if (dataVo.del == 0) {
               // bbsCommentRecycleViewId.text = dataVo.id                      // id는 bbsdto 에서 가져와야하는데
                bbsCommentRecycleViewDate.text = dataVo.wdate
                bbsCommentRecycleViewContent.text = dataVo.content

            } else {

                bbsCommentRecycleViewId.text = ""
                bbsCommentRecycleViewDate.text = ""
                bbsCommentRecycleViewContent.text = "삭제됨"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterCommentList.ItemViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_comment, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapterCommentList.ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}