package com.intern.conjob.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginUser(
    @SerialName("password") val password: String? = null,
    @SerialName("email") val email: String? = null
)