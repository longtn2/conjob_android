package com.intern.conjob.ui.home.profile.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.ProfileTabItem
import com.intern.conjob.databinding.FragmentProfileBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.profile.adapter.ProfileTabAdapter
import com.intern.conjob.ui.home.profile.ProfileViewModel

class ProfileFragment: BaseFragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initListener()
    }

    private fun initViewPager() {
        binding.apply {
            viewPagerProfileTab.adapter = ProfileTabAdapter()
            viewPagerProfileTab.isUserInputEnabled = false
            viewPagerProfileTab.offscreenPageLimit = ProfileTabItem.entries.size
            TabLayoutMediator(tabLayout, viewPagerProfileTab, false) { tab, position ->
                tab.text = ProfileTabItem.entries[position].tabTitle
            }.attach()
        }
    }

    private fun initListener() {
        binding.apply {
            cardViewAvatar.setOnClickListener {
                Toast.makeText(activity as MainActivity, getString(R.string.toast_avatar), Toast.LENGTH_SHORT).show()
            }

            imgBtnSettings.setOnClickListener {
                controller.navigate(R.id.action_homeFragment_to_settingsFragment)
            }

            tvEditProfile.setOnClickListener {
                Toast.makeText(activity as MainActivity, getString(R.string.toast_edit_profile), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
