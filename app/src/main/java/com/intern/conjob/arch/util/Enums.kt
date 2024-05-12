package com.intern.conjob.arch.util

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
