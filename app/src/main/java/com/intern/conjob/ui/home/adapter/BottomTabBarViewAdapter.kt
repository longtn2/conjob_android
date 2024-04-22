package com.intern.conjob.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.intern.conjob.arch.util.BottomTabBarItem

class BottomTabBarViewAdapter(
    fragmentActivity: FragmentActivity,
    private val items: List<BottomTabBarItem>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = items[position].fragment
}