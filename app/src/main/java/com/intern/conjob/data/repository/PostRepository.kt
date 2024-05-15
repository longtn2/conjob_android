package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.PostRemoteDataSource
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.JobResponse
import com.intern.conjob.data.response.PostResponse
import kotlinx.coroutines.flow.Flow

class PostRepository(private val postRemoteDataSource: PostRemoteDataSource) : Repository() {
    fun getPosts(page: Int, limit: Int): Flow<FlowResult<BaseDataResponse<PostResponse>>> = safeFlow {
        postRemoteDataSource.getPosts(page, limit)
    }

    fun getPostsByJobID(jobId: Long): Flow<FlowResult<BaseDataResponse<JobResponse>>> = safeFlow {
        postRemoteDataSource.getPostsByJobID(jobId)
    }
}
