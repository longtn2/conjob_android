package com.intern.conjob.ui.home

import androidx.fragment.app.Fragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.chat.ChatListFragment
import com.intern.conjob.ui.home.matching.fragment.MatchingFragment
import com.intern.conjob.ui.home.notification.NotificationFragment
import com.intern.conjob.ui.home.profile.fragment.ProfileFragment

class HomeViewModel : BaseViewModel() {

    fun initTabItems(): List<Fragment> {
        return listOf(
            MatchingFragment.newInstance(),
            ChatListFragment.newInstance(),
            NotificationFragment.newInstance(),
            ProfileFragment.newInstance()
        )
    }
}
