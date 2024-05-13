package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.LoginResponse

class AuthRemoteDataSource(
    private val api: APIService,
) {
    companion object {
        private var instance: AuthRemoteDataSource? = null

        fun getInstance(): AuthRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = AuthRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun login(loginUser: LoginUser): BaseDataResponse<LoginResponse> = apiCall {
        api.login(loginUser)
    }

    suspend fun forgotPassword(email: String): BaseResponse = apiCall {
        api.forgotPassword(email)
    }

    suspend fun register(registerUser: RegisterUser): BaseResponse = apiCall {
        api.register(registerUser)
    }
}
