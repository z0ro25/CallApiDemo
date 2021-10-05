package com.example.loadmoredemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.Sharedpreferences.SharedPreferencesFavorite
import com.example.loadmoredemo.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vpad = ViewPagerAdapter(this)
        view_Pager2.adapter = vpad
        TabLayoutMediator(tab_Layout,view_Pager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Product"
                1 -> tab.text = "Favorite"
                2 -> tab.text = "Favorote2"
            }
        }.attach()
    }
}
