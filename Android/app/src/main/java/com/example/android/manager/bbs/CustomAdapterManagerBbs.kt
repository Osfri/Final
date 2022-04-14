package com.example.android.manager.bbs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.bbs.BbsDao
import com.example.android.bbs.BbsDetail
import com.example.android.manager.BoardtypeDto

class CustomAdapterManagerBbs(val context: Context, val dataList:ArrayList<BoardtypeDto>):
    RecyclerView.Adapter<CustomAdapterManagerBbs.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val managerRecycleViewBbsTitle = itemView.findViewById<TextView>(R.id.managerRecycleViewBbsTitle)        // 메뉴
        val managerRecycleViewBbsId = itemView.findViewById<TextView>(R.id.managerRecycleViewBbsId)        // 날짜
        val managerRecyclerViewBbsDelete = itemView.findViewById<Button>(R.id.manage_btn_bbsdelete)

        fun bind(dataVo: BoardtypeDto, context: Context){

            managerRecycleViewBbsTitle.text = dataVo.name
            managerRecycleViewBbsId.text = when(dataVo.auth){
                0 -> "관리자쓰기"
                1 -> "모두쓰기"
                else -> ""
            }
            managerRecyclerViewBbsDelete.setOnClickListener {
                AlertDialog.Builder(context).setTitle("댓글 삭제").setMessage("댓글을 삭제하시겠습니까?")
                    .setNegativeButton("아니오",object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {

                        }
                    })
                    .setPositiveButton("예",object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            BbsDao.getInstance().deleteBoardTypeDto(dataVo)
                            context.startActivity(Intent(context,ManagerBbsActivity::class.java))
                        }
                    })
                    .create().show()
            }


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