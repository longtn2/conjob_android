package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateJob(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("budget") val budget: Double,
    @SerializedName("job_type") val jobType: String,
    @SerializedName("location") val location: String,
    @SerializedName("expired_day") val expiredDay: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("status") val status: Int,
)
