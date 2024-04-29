package com.intern.conjob.data.response

import com.google.gson.annotations.SerializedName
import com.intern.conjob.data.model.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerializedName("currentPage") val currentPage: Int? = null,
    @SerializedName("totalPages") val totalPages: Int? = null,
    @SerializedName("pageSize") val pageSize: Int? = null,
    @SerializedName("totalCount") val totalCount: Int? = null,
    @SerializedName("items") val items: List<Post> = listOf()
)
