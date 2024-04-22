package com.intern.conjob.data.response

import com.intern.conjob.data.model.Role
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginResponse(
    @SerialName("token") val token: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("phoneNumber") val phoneNumber: String? = null,
    @SerialName("dob") val dob: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("avatar") val avatar: String? = null,
    @SerialName("roles") val roles: List<Role> = listOf(),
)