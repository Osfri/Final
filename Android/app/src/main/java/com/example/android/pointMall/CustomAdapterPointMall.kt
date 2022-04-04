package com.example.android.pointMall

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.bbs.BbsDetail
import com.example.android.bbs.BbsDto
import com.example.android.bbs.CustomAdapterBbsList
import com.example.android.signin.MemberDao

// (수정,추가_백엔드) 리사이클러뷰 어댑터
class CustomAdapterPointMall(val context:Context, val itemList:MutableList<ShopDto>)
    :RecyclerView.Adapter<CustomAdapterPointMall.ItemViewHolder>()  {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val point_itemlist_title = itemView.findViewById<TextView>(R.id.point_itemlist_title)            // 포인트몰 제목
        val point_itemlist_content = itemView.findViewById<TextView>(R.id.point_itemlist_content)      // 포인트몰 내용
        val point_itemlist_image = itemView.findViewById<ImageView>(R.id.point_itemlist_image)      // 포인트몰 이미지
        val point_itemlist_point = itemView.findViewById<TextView>(R.id.point_itemlist_point)       // (수정,추가_백엔드) 포인트
        val pointlist_btn_detail = itemView.findViewById<Button>(R.id.pointlist_btn_detail)         // (수정,추가_백엔드) 구매버튼


        fun bind(dataVo: ShopDto, context: Context){
            //사진 붙이기 바인딩(img타입)
            if (dataVo.photo != "") {// 사진 데이터가 비어있지 않을때(포토는 문자열이라 " ")
                // 문자열을 넘겨온것을 숫자로 바꿔서 확인해본다 컴퓨터 언어. 그래서 패키지 네임으로 위치 확인하는 문구
                val resourceId = context.resources.getIdentifier(dataVo.photo, "drawable", context.packageName)
                // 그 리소스 아이디 위치가 0보다 컸을때,
                if (resourceId > 0){
                    point_itemlist_image.setImageResource(resourceId)
                    // 만약 리소스 아이디를 못 얻어왓을때, 이미지없을때
                }else{
                    //포토가 없으니까 컴퓨터가 임의로 데이터 포토 로드시켜서 임의로 입력시킨다 - 질문해야됨 어떤 이미지가 넘어오는지
                    Glide.with(itemView).load(dataVo.photo).into(point_itemlist_image)
                    // userPhoto.setImageResource((R.mipmap.ic_launcher_round))    이걸로 해도됨  아무이미지 대체
                }
            } else{
                // 이미지가 없으니까, 아무 이미지나 뿌려라
                point_itemlist_image.setImageResource((R.mipmap.ic_launcher_round))

            }
            point_itemlist_title.text = dataVo.title
            point_itemlist_content.text = dataVo.content
            point_itemlist_point.text = dataVo.price.toString() + "포인트" // (수정,추가_백엔드) 포인트 표시

            // (수정,추가_백엔드) 해당 물품의 재고가 있을 경우
            if(dataVo.cnt>0){
                // (수정,추가_백엔드) 보유 포인트에 따른 구매가능 품목표시
                if(MemberDao.user!!.point!! < dataVo.price){
                    pointlist_btn_detail.text = "구매 불가"
                    pointlist_btn_detail.setBackgroundColor(ContextCompat.getColor(context,R.color.silver))
                    pointlist_btn_detail.setBackgroundResource(R.drawable.button_round_original)
                    pointlist_btn_detail.isEnabled = false
                }else{
                    // (수정,추가_백엔드) 구매버튼 클릭시
                    pointlist_btn_detail.setOnClickListener{
                        Intent(context, PointMallDetailActivity::class.java).apply {
                            putExtra("selectShopItem", dataVo.seq)

                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }.run { context.startActivity(this) }
                    }
                }
            }else{  // 재고가 없을 경우
                pointlist_btn_detail.text = "재고 없음"
                pointlist_btn_detail.setBackgroundColor(ContextCompat.getColor(context,R.color.silver))
                pointlist_btn_detail.setBackgroundResource(R.drawable.button_round_original)
                pointlist_btn_detail.isEnabled = false
            }
        }


    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewgroup.context).inflate(R.layout.view_item_layout_pointmall, viewgroup,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(itemList[position], context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}