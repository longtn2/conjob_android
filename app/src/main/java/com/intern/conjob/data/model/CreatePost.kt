package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePost(
    @SerializedName("title") val title: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("file_name") val fileName: String,
    @SerializedName("file_type") val fileType: String
)
