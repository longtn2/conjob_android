package com.intern.conjob.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.databinding.FragmentHomeBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.adapter.BottomTabBarViewAdapter

class HomeFragment: BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabItems = viewModel.initTabItems()

        binding.apply {
            viewPagerBottomTabBar.adapter = BottomTabBarViewAdapter(activity as MainActivity, tabItems)
            viewPagerBottomTabBar.isUserInputEnabled = false
            viewPagerBottomTabBar.offscreenPageLimit = Constants.PAGE_LIMIT

            TabLayoutMediator(tabLayout, viewPagerBottomTabBar, false) { tab, position ->
                tab.icon = AppCompatResources.getDrawable(activity as MainActivity, tabItems[position].icon)
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
