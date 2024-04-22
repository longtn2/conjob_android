package com.intern.conjob.ui.home.chat

import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class ChatListFragment: BaseFragment(R.layout.fragment_chat_list) {
    override fun getViewModel(): BaseViewModel = BaseViewModel()

    companion object {
        fun newInstance() = ChatListFragment()
    }
}