package com.example.loadmoredemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item.view.*
import java.lang.IllegalArgumentException

//todo interface
//todo different Recyclerview
//todo animation items
//todo multy select items
class UniformAdapter(private  val iner : iUnfadapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val STATICS = "statics/"
    private val BASE_URL = "https://youngkids-dev.acaziasoft.com/"
    private var listUniform: ArrayList<Content> = ArrayList()
    private val TYPE_DATA = 0
    private val TYPE_LOADING = 1
    private var isLoading = false

    fun setData(list: ArrayList<Content>) {
        this.listUniform = list
        notifyDataSetChanged()
    }

    interface iUnfadapter {
        fun onItemClick(position: Int)
    }

    class UniformViewholder(itemView: View,iner : iUnfadapter) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                iner.onItemClick(adapterPosition)
            }
        }
    }


    class LoadingViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class blankViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DATA -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)
                return UniformViewholder(view,iner)
            }
            TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_item, parent, false)
                return LoadingViewholder(view)
            }
            else -> throw IllegalArgumentException("")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_DATA) {
            holder.itemView.tv_name.text = listUniform.get(position).name
            holder.itemView.tv_price.text = listUniform.get(position).price.toString()
            val imgUrl = listUniform.get(position).image
            Picasso.get()
                .load(BASE_URL + STATICS + imgUrl)
                .into(holder.itemView.img_icon)
            val isfavorite = listUniform.get(position).isfavorite
            if (isfavorite == true){
                holder.itemView.favorite_icon.setImageResource(R.drawable.baseline_favorite_red_500_24dp)
            }else holder.itemView.favorite_icon.setImageResource(R.drawable.baseline_favorite_border_red_500_24dp)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (listUniform != null && position == listUniform.size - 1 && isLoading) {
            return TYPE_LOADING
        }
        return TYPE_DATA
    }

    override fun getItemCount(): Int {
        if (listUniform != null)
            return listUniform.size
        return 0
    }

}