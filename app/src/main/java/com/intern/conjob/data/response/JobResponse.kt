package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import com.intern.conjob.data.model.Post

data class JobResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("budget") val budget: Long? = null,
    @SerializedName("job_type") val jobType: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("expired_day") val expiredDay: String? = null,
    @SerializedName("quantity") val quantity: Int? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("posts") val posts: List<Post> = listOf()
)
