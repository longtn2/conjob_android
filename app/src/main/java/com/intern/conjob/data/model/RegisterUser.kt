package com.intern.conjob.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RegisterUser(
    @SerialName("password") val password: String? = null,
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phoneNumber") val phoneNumber: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("dob") val dob: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("fcmToken") val fcmToken: String? = null,
    @SerialName("avatar") val avatar: String? = null
)