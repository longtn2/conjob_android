package com.intern.conjob.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.databinding.FragmentSettingsBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity

class SettingsFragment: BaseFragment(R.layout.fragment_settings) {
    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel by viewModels<SettingsViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolBar.setNavigationOnClickListener {
                controller.popBackStack()
            }

            btnLogout.setOnClickListener {
                viewModel.logout()
                viewModel.clearToken()
                (activity as MainActivity).startActivity(Intent(activity as MainActivity, OnBoardingActivity::class.java))
                (activity as MainActivity).finish()
            }
        }
    }
}
