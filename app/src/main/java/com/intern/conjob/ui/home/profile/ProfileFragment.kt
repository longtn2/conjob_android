package com.intern.conjob.ui.home.profile

import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class ProfileFragment: BaseFragment(R.layout.fragment_profile) {
    override fun getViewModel(): BaseViewModel = BaseViewModel()

    companion object {
        fun newInstance() = ProfileFragment()
    }
}