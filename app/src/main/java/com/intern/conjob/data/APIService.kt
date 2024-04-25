package com.intern.conjob.data

import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.response.LoginResponse
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.TokenResponse
import com.intern.conjob.data.response.TrendingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @GET("search/trending")
    suspend fun getTrending(): Response<TrendingResponse>

    @POST("auth/register")
    suspend fun register(@Body registerUser: RegisterUser): Response<BaseResponse>

    @POST("auth/login")
    suspend fun login(@Body loginUser: LoginUser): Response<LoginResponse>

    @POST("auth/forgot")
    suspend fun forgotPassword(@Query("email") email: String): Response<BaseResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body token: Token): Response<TokenResponse>
}
