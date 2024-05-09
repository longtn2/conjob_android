package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import com.intern.conjob.data.model.Role
import kotlinx.serialization.Serializable

@Serializable
class LoginResponse(
    @SerializedName("token") val token: String? = null,
    @SerializedName("refreshToken") val refreshToken: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("dob") val dob: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("roles") val roles: List<Role> = listOf(),
)
