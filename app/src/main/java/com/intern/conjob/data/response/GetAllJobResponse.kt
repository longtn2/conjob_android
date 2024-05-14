package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import com.intern.conjob.data.model.Job

data class GetAllJobResponse(
    @SerializedName("jobs") val jobs: List<Job> = listOf()
)
