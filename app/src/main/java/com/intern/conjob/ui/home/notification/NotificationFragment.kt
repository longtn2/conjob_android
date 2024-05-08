package com.intern.conjob.ui.home.notification

import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class NotificationFragment: BaseFragment(R.layout.fragment_notification) {
    override fun getViewModel(): BaseViewModel = BaseViewModel()

    companion object {
        fun newInstance() = NotificationFragment()
    }
}