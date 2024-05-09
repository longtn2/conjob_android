package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Job(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("budget") val budget: Double,
    @SerializedName("job_type") val jobType: String,
    @SerializedName("location") val location: String,
    @SerializedName("expired_day") val expiredDay: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("applicants") val applicants: List<Long> = listOf(),
    @SerializedName("user_id") val userId: Long,
    @SerializedName("changed_by") val changedBy: Long? = null,
    @SerializedName("created_by") val createdBy: Long? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
): java.io.Serializable
