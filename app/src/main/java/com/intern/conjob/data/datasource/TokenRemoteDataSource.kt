package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.TokenResponse

class TokenRemoteDataSource(private val api: APIService) {
    companion object {
        private var instance: TokenRemoteDataSource? = null

        fun getInstance(): TokenRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = TokenRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun refreshToken(token: Token): BaseDataResponse<TokenResponse> = apiCall {
        api.refreshToken(token)
    }
}
