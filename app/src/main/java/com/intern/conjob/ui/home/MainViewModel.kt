package com.intern.conjob.ui.home

import com.intern.conjob.arch.util.BottomTabBarItem
import com.intern.conjob.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    private var tabItems: MutableList<BottomTabBarItem> = mutableListOf()

    fun initTabItems(): MutableList<BottomTabBarItem> {
        tabItems.addAll(
            listOf(
                BottomTabBarItem.TAB1,
                BottomTabBarItem.TAB2,
                BottomTabBarItem.TAB3,
                BottomTabBarItem.TAB4,
                BottomTabBarItem.TAB5
            )
        )
        return tabItems
    }
}