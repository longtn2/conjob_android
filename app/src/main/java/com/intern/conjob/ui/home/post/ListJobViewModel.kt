package com.intern.conjob.ui.home.post

import com.intern.conjob.data.model.Job
import com.intern.conjob.ui.base.BaseViewModel

class ListJobViewModel : BaseViewModel() {

    //Mock data
    fun getTempData(): List<Job> {
        return listOf(
            Job(
                id = 0,
                title = "Chạy bộ",
                description = "Chạy 20km",
                budget = 500.0,
                jobType = "Remote",
                location = "Hà Nội",
                expiredDay = "2024-06-22",
                quantity = 6,
                status = 0,
                applicants = listOf(),
                userId = 0
            ),
            Job(
                id = 0,
                title = "Lập trình Java",
                description = "...",
                budget = 50.0,
                jobType = "Remote",
                location = "Đà Nẵng",
                expiredDay = "2024-07-23",
                quantity = 5,
                status = 0,
                applicants = listOf(),
                userId = 0
            ),
            Job(
                id = 0,
                title = "Lập trình C++",
                description = "...",
                budget = 50.0,
                jobType = "Remote",
                location = "Huế",
                expiredDay = "2024-05-22",
                quantity = 4,
                status = 0,
                applicants = listOf(),
                userId = 0
            )
        )
    }

}
