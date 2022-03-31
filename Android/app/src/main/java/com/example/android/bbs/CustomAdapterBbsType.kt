package com.example.android.bbs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CustomAdapterBbsType(val context: Context, val dataList:ArrayList<BbsDto>): RecyclerView.Adapter<CustomAdapterBbsList.ItemViewHolder>() {

