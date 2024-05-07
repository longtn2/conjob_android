package com.intern.conjob.arch.util

import com.intern.conjob.data.model.Post

interface PostOnClickListener {
    fun onDetailClick(post: Post)
    fun onAvatarClick()
    fun onInteractClick()
    fun onCommentClick()
    fun onShareClick()
}
