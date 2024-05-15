package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import com.intern.conjob.data.model.Job
import com.intern.conjob.data.model.Post
import com.intern.conjob.data.model.Role
import com.intern.conjob.data.model.Skill
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("dob") val dob: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("roles") val roles: List<Role> = listOf(),
    @SerializedName("posts") val posts: List<Post> = listOf(),
    @SerializedName("jobs") val jobs: List<Job> = listOf(),
    @SerializedName("skills") val skills: List<Skill> = listOf(),
    @SerializedName("followers") val followers: Long? = null,
    @SerializedName("following") val following: Long? = null,
)
