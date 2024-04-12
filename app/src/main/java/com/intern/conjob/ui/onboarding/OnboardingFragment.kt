package com.intern.conjob.ui.onboarding

import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class OnBoardingFragment : BaseFragment(R.layout.fragment_onboarding) {
    private val viewModel by viewModels<OnBoardingViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel
}