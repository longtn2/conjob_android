package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.response.BaseResponse

class RegisterUserRemoteDataSource(private val api: APIService) {
    companion object {
        private var instance: RegisterUserRemoteDataSource? = null

        fun getInstance(): RegisterUserRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = RegisterUserRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun register(registerUser: RegisterUser): BaseResponse = apiCall {
        api.register(registerUser)
    }
}