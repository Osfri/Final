package com.example.android.bbs

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.signin.MemberDao

class CustomAdapterCommentList(val context: Context, val dataList:ArrayList<BbsDto>):RecyclerView.Adapter<CustomAdapterCommentList.ItemViewHolder>()  {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val bbsCommentRecycleViewId = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewId)            // 아이디
        val bbsCommentRecycleViewDate = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewDate)      // 날짜
        val bbsCommentRecycleViewContent = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewContent)      // 내용

        val bbsCommentRecyclerViewWriter = itemView.findViewById<TextView>(R.id.bbsCommentRecycleViewWriter)
        val bbsCommentRecyclerViewDelete = itemView.findViewById<Button>(R.id.bbsCommentRecycleViewDelete)
        val bbsCommentRecyclerViewUpdate = itemView.findViewById<Button>(R.id.bbsCommentRecycleViewUpdate)

        val bbsCommentRecyclerViewUpdateText = itemView.findViewById<EditText>(R.id.bbsCommentRecycleViewUpdateText)
        val bbsCommentRecyclerViewUpdateDone = itemView.findViewById<Button>(R.id.bbsCommentRecycleViewUpdateDone)

        // 2022-04-06 00:00:00
        fun bind(dataVo:BbsDto){
            var text = ""
            bbsCommentRecyclerViewUpdateText.isGone
            bbsCommentRecyclerViewUpdateDone.isGone
            if (dataVo.id.toString() != MemberDao.user?.id.toString()){
                bbsCommentRecyclerViewWriter.isInvisible
                bbsCommentRecyclerViewUpdate.isInvisible
                if (dataVo.id.toString() != MemberDao.user?.id.toString() || MemberDao.user?.auth != 0){
                    bbsCommentRecyclerViewDelete.isInvisible
                }
            }
            if (dataVo.del == 0) {
                bbsCommentRecycleViewId.text = dataVo.id
                val split = dataVo.wdate!!.split(" ")[0]
                bbsCommentRecycleViewDate.text = split
                bbsCommentRecycleViewContent.text = dataVo.content
            } else {

                bbsCommentRecycleViewId.text = ""
                bbsCommentRecycleViewDate.text = ""
                bbsCommentRecycleViewContent.text = "삭제됨"
            }
            bbsCommentRecyclerViewDelete.setOnClickListener {
                BbsDao.getInstance().deleteBbs(dataVo.seq)
                val data = BbsDetail.Detaildata
                BbsDetail.getInstance().commentlist(data!!)
            }
            bbsCommentRecyclerViewUpdate.setOnClickListener {
                bbsCommentRecyclerViewUpdateText.isVisible
                bbsCommentRecyclerViewUpdateDone.isVisible
                bbsCommentRecyclerViewUpdateText.setText(dataVo.content)
            }
            bbsCommentRecyclerViewUpdateDone.setOnClickListener {
                val dto = BbsDto(dataVo.seq,dataVo.id,"댓글",text,0,dataVo.wdate,0,dataVo.type,dataVo.code,1,dataVo.gr,"")
                BbsDao.getInstance().updatecomment(dto)
                bbsCommentRecyclerViewUpdateText.isGone
                bbsCommentRecyclerViewUpdateDone.isGone
                val data = BbsDetail.Detaildata
                BbsDetail.getInstance().commentlist(data!!)
            }
            if (bbsCommentRecyclerViewUpdateText.isVisible){
                bbsCommentRecyclerViewUpdateText.addTextChangedListener(object :TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {
                        text = bbsCommentRecyclerViewUpdateText.text.toString()
                    }
                })
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterCommentList.ItemViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout_comment, parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapterCommentList.ItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}