package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.PostResponse

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
}
