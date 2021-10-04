package com.example.loadmoredemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail_layout.view.*
import kotlinx.android.synthetic.main.product_item.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var listFavorite : ArrayList<Content> = ArrayList()
    private val BASE_URL = "https://youngkids-dev.acaziasoft.com/statics/"

    fun setFavorite(list: ArrayList<Content>){
        this.listFavorite = list
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.itemView.tv_name.text = listFavorite.get(position).name
        holder.itemView.tv_price.text = listFavorite.get(position).price.toString()
        val imgUrl = listFavorite.get(position).image
        Picasso.get()
            .load(BASE_URL + imgUrl)
            .into(holder.itemView.img_icon)
        if (listFavorite.get(position).isfavorite == true){
            holder.itemView.favorite_icon.setImageResource(R.drawable.baseline_favorite_red_500_24dp)
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }
}