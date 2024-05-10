package com.intern.conjob.ui.home.profile.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.ProfileTabItem
import com.intern.conjob.databinding.FragmentProfileBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.profile.adapter.ProfileTabAdapter
import com.intern.conjob.ui.home.profile.ProfileViewModel
import kotlinx.coroutines.flow.launchIn
import java.text.SimpleDateFormat
import java.util.Locale

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
        viewModel.getUserProfile().onSuccess {
            binding.apply {
                it.data?.let { data ->
                    tvUserName.text = getString(R.string.user_name, data.lastName, data.firstName)
                    tvPhone.text = HtmlCompat.fromHtml(getString(R.string.profile_phone_number_data, data.phoneNumber), HtmlCompat.FROM_HTML_MODE_COMPACT)
                    tvAddress.text = HtmlCompat.fromHtml(getString(R.string.profile_address_data, data.address), HtmlCompat.FROM_HTML_MODE_COMPACT)
                    tvDob.text = HtmlCompat.fromHtml(getString(R.string.profile_date_of_birth_data, data.dob?.let { dob ->
                        SimpleDateFormat(Constants.DATE_FORMAT, Locale.US).parse(dob)?.let { it1 ->
                            SimpleDateFormat(Constants.POST_VIEW_DATE_FORMAT, Locale.US).format(it1)
                        }
                    } ?: ""), HtmlCompat.FROM_HTML_MODE_COMPACT)
                    tvGender.text = HtmlCompat.fromHtml(getString(R.string.profile_gender_data, data.gender), HtmlCompat.FROM_HTML_MODE_COMPACT)
                    tvEmail.text = HtmlCompat.fromHtml(getString(R.string.profile_email_data, data.email), HtmlCompat.FROM_HTML_MODE_COMPACT)
                    val imageSize = (activity as MainActivity).convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE)
                    Glide.with(activity as MainActivity).load(data.avatar)
                        .override(imageSize)
                        .into(imgAvatar)
                }
            }
        }.launchIn(lifecycleScope)
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
                findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            }

            tvEditProfile.setOnClickListener {
                Toast.makeText(activity as MainActivity, getString(R.string.toast_edit_profile), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
