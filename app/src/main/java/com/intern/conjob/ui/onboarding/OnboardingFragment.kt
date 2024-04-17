package com.intern.conjob.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.databinding.FragmentOnboardingBinding
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class OnBoardingFragment : BaseFragment(R.layout.fragment_onboarding) {
    private val binding by viewBinding(FragmentOnboardingBinding::bind)
    private val viewModel by viewModels<OnBoardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                controller.navigate(R.id.action_OnBoardingFragment_to_RegisterFragment)
            }

            btnLogin.setOnClickListener {
                controller.navigate(R.id.action_OnBoardingFragment_to_LoginFragment)
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}