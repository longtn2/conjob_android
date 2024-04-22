package com.intern.conjob.ui

import android.annotation.SuppressLint
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.PAGE_LIMIT
import com.intern.conjob.databinding.ActivityMainBinding
import com.intern.conjob.ui.home.MainViewModel
import com.intern.conjob.ui.home.adapter.BottomTabBarViewAdapter
import com.intern.conjob.ui.base.BaseActivity

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override val viewModel = MainViewModel()

    override fun initialize() {
        initViewPager()
    }

    @SuppressLint("WrongConstant")
    private fun initViewPager() {
        val tabItems = viewModel.initTabItems()
        binding.apply {
            viewPagerBottomTabBar.adapter = BottomTabBarViewAdapter(this@MainActivity, tabItems)
            viewPagerBottomTabBar.isUserInputEnabled = false
            viewPagerBottomTabBar.offscreenPageLimit = PAGE_LIMIT

            TabLayoutMediator(tabLayout, viewPagerBottomTabBar, false) { tab, position ->
                tab.icon = AppCompatResources.getDrawable(this@MainActivity, tabItems[position].icon)
            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let { viewPagerBottomTabBar.setCurrentItem(it, false) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })
        }

    }

}

