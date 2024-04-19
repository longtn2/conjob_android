package com.intern.conjob.data

import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.response.LoginResponse
import com.intern.conjob.data.response.TrendingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("search/trending")
    suspend fun getTrending(): Response<TrendingResponse>

    @POST("auth/login")
    suspend fun login(@Body loginUser: LoginUser): Response<LoginResponse>
}
