package com.intern.conjob.ui.home.match

import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class MatchingFragment : BaseFragment(R.layout.fragment_matching) {
    override fun getViewModel(): BaseViewModel = BaseViewModel()

    companion object {
        fun newInstance() = MatchingFragment()
    }
}