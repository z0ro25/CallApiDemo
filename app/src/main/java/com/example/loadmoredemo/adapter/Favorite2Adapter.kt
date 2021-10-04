package com.example.loadmoredemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmoredemo.R
import com.example.loadmoredemo.database.ContentEntities
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item.view.*

class Favorite2Adapter : RecyclerView.Adapter<Favorite2Adapter.Favorite2Viewholder>() {
    private var listFavorite2 : ArrayList<ContentEntities> = ArrayList()
    private val BASE_URL = "https://youngkids-dev.acaziasoft.com/statics/"


    fun setUpData(list: ArrayList<ContentEntities>){
        this.listFavorite2 = list
        notifyDataSetChanged()
    }
    class Favorite2Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Favorite2Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return Favorite2Viewholder(view)
    }

    override fun onBindViewHolder(holder: Favorite2Viewholder, position: Int) {
        holder.itemView.tv_name.text = listFavorite2.get(position).name
        holder.itemView.tv_price.text = listFavorite2.get(position).price.toString()
        Picasso.get()
            .load(BASE_URL + listFavorite2.get(position).image)
            .into(holder.itemView.img_icon)
        if (listFavorite2.get(position).isfavorite == true)
        {
            holder.itemView.favorite_icon.setImageResource(R.drawable.baseline_favorite_red_500_24dp)
        }else  holder.itemView.favorite_icon.setImageResource(R.drawable.baseline_favorite_border_red_500_24dp)
    }

    override fun getItemCount(): Int {
        if (listFavorite2.isEmpty()){
            return 0
        }
        return listFavorite2.size
    }
}