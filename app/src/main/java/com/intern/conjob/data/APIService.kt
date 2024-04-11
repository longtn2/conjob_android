package com.intern.conjob.data

import com.intern.conjob.data.response.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("search/trending")
    suspend fun getTrending(): Response<TrendingResponse>
}
