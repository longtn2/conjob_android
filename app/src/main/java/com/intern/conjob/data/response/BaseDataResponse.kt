package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class BaseDataResponse<T>(
    @SerializedName("data") val data: T? = null
)
