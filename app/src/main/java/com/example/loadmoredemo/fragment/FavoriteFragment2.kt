package com.example.loadmoredemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.loadmoredemo.R
import com.example.loadmoredemo.adapter.Favorite2Adapter
import com.example.loadmoredemo.database.ContentDataBase
import com.example.loadmoredemo.database.ContentEntities
import kotlinx.android.synthetic.main.favorite2_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteFragment2 : Fragment() {
    private var listFavorite2 : ArrayList<ContentEntities> = ArrayList()
    private val adapter = Favorite2Adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite2_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RCV_favorite2.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        RCV_favorite2.adapter = adapter
        getData()
    }

    private fun getData() {
        GlobalScope.launch {
            val items = Room.databaseBuilder(
                requireContext().applicationContext,
                ContentDataBase::class.java, "database-name"
            ).fallbackToDestructiveMigration().build().userDao().getContent()

            listFavorite2.addAll(items)
            launch(Dispatchers.Main) {
                adapter.setUpData(listFavorite2)
            }

        }
    }
}