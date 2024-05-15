package com.intern.conjob.ui.home

import com.intern.conjob.arch.util.BottomTabBarItem
import com.intern.conjob.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private var tabItems: List<BottomTabBarItem> = listOf(
        BottomTabBarItem.TAB1,
        BottomTabBarItem.TAB2,
        BottomTabBarItem.TAB4,
        BottomTabBarItem.TAB5
    )

    fun initTabItems(): List<BottomTabBarItem> {
        return tabItems
    }
}
