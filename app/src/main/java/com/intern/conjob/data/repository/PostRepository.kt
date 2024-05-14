package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.PostRemoteDataSource
import com.intern.conjob.data.model.CreatePost
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.CreatePostResponse
import com.intern.conjob.data.response.JobResponse
import com.intern.conjob.data.response.PostResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class PostRepository(private val postRemoteDataSource: PostRemoteDataSource) : Repository() {
    fun getPosts(page: Int, limit: Int): Flow<FlowResult<BaseDataResponse<PostResponse>>> = safeFlow {
        postRemoteDataSource.getPosts(page, limit)
    }

    fun getPostsByJobID(jobId: Long): Flow<FlowResult<BaseDataResponse<JobResponse>>> = safeFlow {
        postRemoteDataSource.getPostsByJobID(jobId)
    }

    fun createPost(createPost: CreatePost): Flow<FlowResult<BaseDataResponse<CreatePostResponse>>> = safeFlow {
        postRemoteDataSource.createPost(createPost)
    }

    fun addJobToPost(jobId: Long, postId: Long): Flow<FlowResult<BaseResponse>> = safeFlow {
        postRemoteDataSource.addJobToPost(jobId, postId)
    }

    fun uploadFile(url: String, file: RequestBody): Flow<FlowResult<Unit>> = safeFlow {
        postRemoteDataSource.uploadFile(url, file)
    }

}

