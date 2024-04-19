package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.response.BaseResponse

class ForgotPasswordRemoteDataSource(
    private val api: APIService,
) {
    companion object {
        private var instance: ForgotPasswordRemoteDataSource? = null

        fun getInstance(): ForgotPasswordRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = ForgotPasswordRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun forgotPassword(email: String): BaseResponse = apiCall {
        api.forgotPassword(email)
    }
}
