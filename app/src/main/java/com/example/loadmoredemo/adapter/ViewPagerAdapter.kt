package com.example.loadmoredemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.loadmoredemo.fragment.FavoriteFragment
import com.example.loadmoredemo.fragment.FavoriteFragment2
import com.example.loadmoredemo.fragment.ListProductFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return ListProductFragment()
            1-> return FavoriteFragment()
            2-> return FavoriteFragment2()
        }
        return ListProductFragment()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}