package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UploadFile(
    @SerializedName("file_name") val fileName: String,
    @SerializedName("file_type") val fileType: String
)