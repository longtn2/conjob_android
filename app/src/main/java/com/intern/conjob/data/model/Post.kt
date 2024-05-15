package com.intern.conjob.data.model

data class Post(
    val id: Long,
    val title: String,
    val caption: String,
    val author: String,
    val url: String,
    val type: String,
)
