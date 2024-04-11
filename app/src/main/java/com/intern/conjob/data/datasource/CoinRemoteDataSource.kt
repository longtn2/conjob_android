package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.response.TrendingResponse

class CoinRemoteDataSource(
    private val api: APIService,
) {
    companion object {
        private var instance: CoinRemoteDataSource? = null

        fun getInstance(): CoinRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = CoinRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun getTrending(): TrendingResponse = apiCall {
        api.getTrending()
    }
}
