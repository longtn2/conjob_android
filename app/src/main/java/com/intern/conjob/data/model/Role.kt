package com.intern.conjob.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    @SerialName("id") val id: Int,
    @SerialName("roleName") val roleName: String,
    @SerialName("roleDescription") val roleDescription: String,
    @SerialName("roleAccessLevel") val roleAccessLevel: Int,
    @SerialName("created_at") val createAt: Int,
    @SerialName("updated_at") val updatedAt: Int,
)
