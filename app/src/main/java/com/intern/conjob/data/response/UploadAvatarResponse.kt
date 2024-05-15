package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UploadAvatarResponse(
    @SerializedName("url") val url: String? = null,
    @SerializedName("method") val method: String? = null,
    @SerializedName("expired") val expired: String? = null
)
