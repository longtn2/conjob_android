package com.intern.conjob.arch.util

import androidx.fragment.app.Fragment
import com.intern.conjob.ui.home.chat.ChatListFragment
import com.intern.conjob.ui.home.matching.fragment.MatchingFragment
import com.intern.conjob.ui.home.notification.NotificationFragment
import com.intern.conjob.ui.home.profile.fragment.ProfileFragment

enum class BottomTabBarItem(
    val fragment: Fragment
) {
    TAB1(MatchingFragment.newInstance()),
    TAB2(ChatListFragment.newInstance()),
    TAB4(NotificationFragment.newInstance()),
    TAB5(ProfileFragment.newInstance())
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

enum class JobTypeEnum(
    val jobType: String
) {
    UNSELECTED("--Loại công việc--"),
    REMOTE("Remote"),
    FULL_TIME("FullTime"),
    PART_TIME("PartTime"),
    ONSITE("Onsite"),
    HYBRID("Hybrid"),
    FREELANCE("Freelance"),
}
