package com.intern.conjob.ui.home.profile.fragment

import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.profile.OtherProfileViewModel

class OtherProfileFragment : BaseFragment(R.layout.fragment_other_profile) {
    private val viewModel by viewModels<OtherProfileViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel
}
