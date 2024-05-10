package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.UserInfoResponse

class UserRemoteDataSource(private val api: APIService) {
    companion object {
        private var instance: UserRemoteDataSource? = null

        fun getInstance(): UserRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = UserRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun getUserInfo(userId: Long): BaseDataResponse<UserInfoResponse> = apiCall {
        api.getUserInfo(userId)
    }

    suspend fun getProfile(): BaseDataResponse<UserInfoResponse> = apiCall {
        api.getProfile()
    }
}
