package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName

data class CreatePostResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("method") val method: String? = null,
    @SerializedName("expired") val expired: String? = null
)
