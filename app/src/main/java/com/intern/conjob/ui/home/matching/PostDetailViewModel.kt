package com.intern.conjob.ui.home.matching

import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.data.datasource.PostRemoteDataSource
import com.intern.conjob.data.model.Post
import com.intern.conjob.data.repository.PostRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.JobResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostDetailViewModel(
    private val postRepository: PostRepository = PostRepository(PostRemoteDataSource.getInstance())
): BaseViewModel() {

    private val _posts: MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    var posts: StateFlow<List<Post>> = _posts

    fun setFirstPost(post: Post?) {
        post?.let {
            _posts.value = listOf(post)
        }
    }

    fun getPostsByJobID(jobId: Long): Flow<FlowResult<BaseDataResponse<JobResponse>>> {
        return postRepository.getPostsByJobID(jobId).onSuccess {
            _posts.value = it.data?.posts!!
        }
    }
}
