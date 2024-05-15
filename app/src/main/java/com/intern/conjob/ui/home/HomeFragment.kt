package com.intern.conjob.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.databinding.FragmentHomeBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.adapter.BottomTabBarViewAdapter

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private var lastTab: TabLayout.Tab? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    @SuppressLint("WrongConstant")
    private fun initAdapter() {
        val tabItems = viewModel.initTabItems()
        binding.apply {
            viewPagerBottomTabBar.adapter = BottomTabBarViewAdapter(activity as MainActivity, tabItems)
            viewPagerBottomTabBar.isUserInputEnabled = false
            viewPagerBottomTabBar.offscreenPageLimit = Constants.PAGE_LIMIT

            tabLayout.getTabAt(2)?.view?.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPublishPostFragment())
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        if (it != 2) {
                            when (it > 2) {
                                true -> viewPagerBottomTabBar.setCurrentItem(it - 1, false)
                                false -> viewPagerBottomTabBar.setCurrentItem(it, false)
                            }
                            lastTab = tab
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })

            lastTab?.let {
                tabLayout.selectTab(it)
            }
        }
    }
}
