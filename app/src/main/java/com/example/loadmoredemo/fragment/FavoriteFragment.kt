package com.example.loadmoredemo.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.R
import com.example.loadmoredemo.Sharedpreferences.SharedPreferencesFavorite
import com.example.loadmoredemo.adapter.FavoriteAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.favorite_fragment_layout.*
import kotlin.math.log

class FavoriteFragment : Fragment() {
    private var listFavoriteproduct: ArrayList<Content> = ArrayList()
    private val adapter = FavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RCV_favorite.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RCV_favorite.adapter = adapter
        getFavorite()

    }

    private fun getFavorite() {
        val sharedFAV = SharedPreferencesFavorite(requireActivity().application)
        val gson = Gson()
        val json = sharedFAV.getListFAV("SAVE_KEY","")
        val favOject: HashMap<String,Content> = gson.fromJson(json, object : TypeToken<HashMap<String,Content>>() {}.type)
        Log.i("favOject",favOject.toString())
        favOject.forEach {
            listFavoriteproduct.add(it.value)
        }
        adapter.setFavorite(listFavoriteproduct)
    }
}
