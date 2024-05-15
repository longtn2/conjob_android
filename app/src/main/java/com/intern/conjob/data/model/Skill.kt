package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Skill(
    @SerializedName("id") val id: Long
)
