package com.intern.conjob.arch.util

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.intern.conjob.R
import com.intern.conjob.ui.home.chat.ChatListFragment
import com.intern.conjob.ui.home.matching.fragment.MatchingFragment
import com.intern.conjob.ui.home.notification.NotificationFragment
import com.intern.conjob.ui.home.post.CreatePostFragment
import com.intern.conjob.ui.home.profile.fragment.ProfileFragment

enum class BottomTabBarItem(
    val fragment: Fragment,
    @DrawableRes val icon: Int
) {
    TAB1(MatchingFragment.newInstance(), R.drawable.ic_home),
    TAB2(ChatListFragment.newInstance(), R.drawable.ic_chat),
    TAB3(CreatePostFragment.newInstance(), R.drawable.ic_plus),
    TAB4(NotificationFragment.newInstance(), R.drawable.ic_notification),
    TAB5(ProfileFragment.newInstance(), R.drawable.ic_profile)
}

enum class ErrorMessage(val message: String) {
    NOT_FOUND_404("NOT FOUND 404"),
    BAD_GATEWAY_502("BAD GATEWAY 502"),
    SERVER_ERROR_500("SERVER ERROR 500")
}

enum class FileType(val type: String) {
    IMAGE("Img"),
    VIDEO("Video")
}

enum class ProfileTabItem(
    val tabTitle: String
) {
    PostTab("Bài đăng"),
    SkillTab("Kỹ năng"),
    JobTab("Công việc")
}
