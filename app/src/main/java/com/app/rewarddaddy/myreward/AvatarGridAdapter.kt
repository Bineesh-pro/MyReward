package com.app.rewarddaddy.myreward

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class AvatarGridAdapter(var acti:Avatar,var avatarList:Array<Int>) : BaseAdapter() {
    override fun getCount(): Int {
        return avatarList.size
    }

    override fun getItem(p0: Int): Any {
        return avatarList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(p2?.context).inflate(R.layout.avatar_card_view,p2,false)
        var pic:ImageView = view.findViewById(R.id.card_avatar)
        pic.setImageResource(avatarList[p0])
        pic.setOnClickListener{
            val dataEditor = acti.getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()
            dataEditor.putInt("user_avatar",avatarList[p0])
            dataEditor.apply()
            acti.updateUI(avatarList[p0])
        }
        return view

    }


}