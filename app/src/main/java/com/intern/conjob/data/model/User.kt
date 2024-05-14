package com.intern.conjob.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("dob") val dob: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
): java.io.Serializable
