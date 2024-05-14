package com.intern.conjob.ui.home.profile.fragment

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.ProfileTabItem
import com.intern.conjob.databinding.FragmentOtherProfileBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.profile.OtherProfileViewModel
import com.intern.conjob.ui.home.profile.adapter.ProfileTabAdapter
import kotlinx.coroutines.flow.launchIn
import java.text.SimpleDateFormat
import java.util.Locale

class OtherProfileFragment : BaseFragment(R.layout.fragment_other_profile) {
    private val binding by viewBinding(FragmentOtherProfileBinding::bind)
    private val viewModel by viewModels<OtherProfileViewModel>()
    private val args by navArgs<OtherProfileFragmentArgs>()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        binding.imgBtnBackArrow.setOnClickListener {
            controller.popBackStack()
        }
        getUserProfile()
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

    private fun getUserProfile() {
        binding.apply {
            viewModel.getUserInfoById(args.userId).onSuccess {
                it.data?.let { data ->
                    tvUserName.text = getString(R.string.user_name, data.lastName, data.firstName)
                    tvPhone.text = HtmlCompat.fromHtml(
                        getString(
                            R.string.profile_phone_number_data,
                            data.phoneNumber
                        ),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    tvAddress.text = HtmlCompat.fromHtml(
                        getString(R.string.profile_address_data, data.address),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    tvDob.text = HtmlCompat.fromHtml(
                        getString(
                            R.string.profile_date_of_birth_data,
                            data.dob?.let { dob ->
                                SimpleDateFormat(Constants.DATE_FORMAT, Locale.US).parse(dob)
                                    ?.let { it1 ->
                                        SimpleDateFormat(
                                            Constants.POST_VIEW_DATE_FORMAT,
                                            Locale.US
                                        ).format(it1)
                                    }
                            } ?: ""),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    tvGender.text = HtmlCompat.fromHtml(
                        getString(R.string.profile_gender_data, data.gender),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    tvEmail.text = HtmlCompat.fromHtml(
                        getString(R.string.profile_email_data, data.email),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    val imageSize =
                        (activity as MainActivity).convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE)
                    Glide.with(activity as MainActivity).load(data.avatar).override(imageSize)
                        .into(imgAvatar)
                }
            }.launchIn(lifecycleScope)
        }
    }
}
