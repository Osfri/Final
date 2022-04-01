package com.example.android.lunch

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment



class ChatOneFragment : Fragment(com.example.android.R.layout.fragment_chat_one) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.android.R.layout.fragment_chat_one, container, false)
    }
}