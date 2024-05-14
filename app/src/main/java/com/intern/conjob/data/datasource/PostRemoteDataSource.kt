package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.CreatePost
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.CreatePostResponse
import com.intern.conjob.data.response.JobResponse
import com.intern.conjob.data.response.PostResponse
import okhttp3.RequestBody

class PostRemoteDataSource(private val api: APIService) {
    companion object {
        private var instance: PostRemoteDataSource? = null

        fun getInstance(): PostRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = PostRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun getPosts(page: Int, limit: Int): BaseDataResponse<PostResponse> = apiCall {
        api.getPosts(page, limit)
    }

    suspend fun getPostsByJobID(jobId: Long): BaseDataResponse<JobResponse> = apiCall {
        api.getJob(jobId)
    }

    suspend fun createPost(createPost: CreatePost): BaseDataResponse<CreatePostResponse> = apiCall {
        api.createPost(createPost)
    }

    suspend fun addJobToPost(jobId: Long, postId: Long): BaseResponse = apiCall {
        api.addJobToPost(jobId, postId)
    }

    suspend fun uploadFile(url: String, file: RequestBody): Unit = apiCall {
        api.uploadFile(url, file)
    }
}
