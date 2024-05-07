package com.intern.conjob.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("author") val author: String,
    @SerializedName("avatar_author") val avatar: String,
    @SerializedName("job") val job: Job? = null,
    @SerializedName("type_file") val type: String,
    @SerializedName("name_file") val name: String,
    @SerializedName("url_file") val url: String,
    @SerializedName("create_at") val createAt: String? = null,
    @SerializedName("likes") val likes: Long,
): java.io.Serializable {
    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}
