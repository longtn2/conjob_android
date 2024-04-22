package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.response.LoginResponse

class LoginRemoteDataSource(
    private val api: APIService,
) {
    companion object {
        private var instance: LoginRemoteDataSource? = null

        fun getInstance(): LoginRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = LoginRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun login(loginUser: LoginUser): LoginResponse = apiCall {
        api.login(loginUser)
    }
}