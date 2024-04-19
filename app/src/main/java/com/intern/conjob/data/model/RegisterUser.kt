package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class RegisterUser(
    @SerializedName("password") val password: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("dob") val dob: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("avatar") val avatar: String? = null
)