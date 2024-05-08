package com.intern.conjob.ui.home.post

import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class CreatePostFragment: BaseFragment(R.layout.fragment_create_post) {
    override fun getViewModel(): BaseViewModel = BaseViewModel()

    companion object {
        fun newInstance() = CreatePostFragment()
    }
}