package com.example.android.pointMall

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.bbs.BbsDetail
import com.example.android.bbs.BbsDto
import com.example.android.bbs.CustomAdapterBbsList

class CustomAdapterPointMall():RecyclerView.Adapter<CustomAdapterPointMall.ItemViewHolder>()  {

    // 임시 데이터 = 레이아웃 보기위한 정보   지워야 합니다
    var titles = arrayOf("one", "two", "three", "four", "five")
    var details = arrayOf("Como melhorar a usabilidade dos seus sistemas de pagamento - Por", "Item two", "Item three", "Item four", "Itme five")
    var images = intArrayOf(R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground)



    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val point_itemlist_title = itemView.findViewById<TextView>(R.id.point_itemlist_title)            // 포인트몰 제목
        val point_itemlist_content = itemView.findViewById<TextView>(R.id.point_itemlist_content)      // 포인트몰 내용
        val point_itemlist_image = itemView.findViewById<ImageView>(R.id.point_itemlist_image)      // 포인트몰 이미지


        fun bind(dataVo: PointMallDto, context: Context){
            //사진 붙이기 바인딩(img타입)
            if (dataVo.image != "") {// 사진 데이터가 비어있지 않을때(포토는 문자열이라 " ")
                // 문자열을 넘겨온것을 숫자로 바꿔서 확인해본다 컴퓨터 언어. 그래서 패키지 네임으로 위치 확인하는 문구
                val resourceId = context.resources.getIdentifier(dataVo.image, "drawable", context.packageName)
                // 그 리소스 아이디 위치가 0보다 컸을때,
                if (resourceId > 0){
                    point_itemlist_image.setImageResource(resourceId)
                    // 만약 리소스 아이디를 못 얻어왓을때, 이미지없을때
                }else{
                    //포토가 없으니까 컴퓨터가 임의로 데이터 포토 로드시켜서 임의로 입력시킨다 - 질문해야됨 어떤 이미지가 넘어오는지
                    Glide.with(itemView).load(dataVo.image).into(point_itemlist_image)
                    // userPhoto.setImageResource((R.mipmap.ic_launcher_round))    이걸로 해도됨  아무이미지 대체
                }
            } else{
                // 이미지가 없으니까, 아무 이미지나 뿌려라
                point_itemlist_image.setImageResource((R.mipmap.ic_launcher_round))

            }
            point_itemlist_title.text = dataVo.title
            point_itemlist_content.text = dataVo.content


/*
            itemView.setOnClickListener{
                if(dataVo!!.del == 0) {                      //    isFocusable 터치해서 접근여부
                    Intent(context, BbsDetail::class.java).apply {

                        putExtra("data", dataVo)

                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { context.startActivity(this) }
                } else{

                }
            }*/

        }


    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewgroup.context).inflate(R.layout.view_item_layout_pointmall, viewgroup,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.point_itemlist_title.setText(titles.get(position))
        holder.point_itemlist_image.setImageResource(images.get(position))
        holder.point_itemlist_content.setText(details.get(position))
    }

    override fun getItemCount(): Int {
        return titles.size
    }


}