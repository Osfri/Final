package com.example.android.chat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.R
import com.example.android.chat.ChatSingleton

class AccountFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(R.layout.fragment_account, container, false)

        val userId: TextView = view.findViewById<TextView>(R.id.accountfragment_userId)
        val userName: TextView = view.findViewById<TextView>(R.id.accountfragment_userName)
        val userEmail: TextView = view.findViewById<TextView>(R.id.accountfragment_userEmail)
        val userPhonenumber: TextView = view.findViewById<TextView>(R.id.accountfragment_userPhonenumber)
        val userCode: TextView = view.findViewById<TextView>(R.id.accountfragment_userCode)

        userId.text = ChatSingleton.getInstance().loginUserInfo.id
        userName.text = ChatSingleton.getInstance().loginUserInfo.name
        userEmail.text = ChatSingleton.getInstance().loginUserInfo.email
        userPhonenumber.text = ChatSingleton.getInstance().loginUserInfo.phonenumber
        userCode.text = ChatSingleton.getInstance().getLoginUserHospitalInfo(ChatSingleton.getInstance().loginUserInfo).name

        return view
    }
}