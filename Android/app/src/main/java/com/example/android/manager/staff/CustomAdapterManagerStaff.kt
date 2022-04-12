package com.example.android.manager.staff

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.signin.MemberDao
import com.example.android.signin.MemberDto

class CustomAdapterManagerStaff(val context: Context, val dataList:ArrayList<MemberDto>):
    RecyclerView.Adapter<CustomAdapterManagerStaff.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val managerRecycleViewStaffName = itemView.findViewById<TextView>(R.id.managerRecycleViewStaffName)        // 가입승인 이름
        val managerRecyclerViewOut = itemView.findViewById<Button>(R.id.manage_btn_staffdelete)


        fun bind(dataVo: MemberDto, context: Context){

            managerRecycleViewStaffName.text = "${dataVo.name} / ${dataVo.id} / ${dataVo.phonenumber}"
            managerRecyclerViewOut.setOnClickListener {
                MemberDao.getInstance().nojoin(dataVo)
                context.startActivity(Intent(context,ManagerStaffActivity::class.java))
            }
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