package com.example.loadmoredemo.Sharedpreferences

import android.content.Context
import com.example.loadmoredemo.ApiModel.Content
import com.google.gson.Gson

class SharedPreferencesFavorite(val contex : Context) {
    fun saveListFAV(key:String, value: Map<String, Content>){
        val sharedPreferences = contex.getSharedPreferences("shared",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(value)
        editor.putString(key,json)
        editor.apply()
    }

    fun getListFAV(key: String,defaul :String): String? {
        val sharedPreferences = contex.getSharedPreferences("shared",Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,defaul)
    }
}