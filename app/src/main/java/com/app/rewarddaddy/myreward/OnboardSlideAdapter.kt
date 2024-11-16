package com.app.rewarddaddy.myreward

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class OnboardSlideAdapter(val context: Context,val modelList:List<Int>) :
    RecyclerView.Adapter<OnboardSlideAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.onboard_view,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pic.setImageResource(modelList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pic = itemView.findViewById<ImageView>(R.id.onboard_center_pic)
    }

}