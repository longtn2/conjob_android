package com.intern.conjob.arch.modules

import com.intern.conjob.BuildConfig
import com.intern.conjob.arch.util.SharedPref
import com.intern.conjob.arch.util.TokenAuthenticator
import com.intern.conjob.data.APIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit

open class ApiClient {
    private fun client(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.interceptors().add(Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)

            val request = if (original.url.toString().contains(BuildConfig.API_URL)) {
                requestBuilder
                    .addHeader(TokenAuthenticator.AUTHORIZATION, TokenAuthenticator.BEARER + SharedPref.getToken())
                    .build()
            } else {
                requestBuilder.build()
            }

            chain.withConnectTimeout(40, TimeUnit.SECONDS)
                .withWriteTimeout(40, TimeUnit.SECONDS)
                .withReadTimeout(40, TimeUnit.SECONDS)
                .proceed(request)
        })

        httpClient.authenticator(TokenAuthenticator.getInstance())

        httpClient.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }

    private val apiClient: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client())
        .build()

    val apiService: APIService by lazy {
        apiClient.create(APIService::class.java)
    }

    companion object {
        private var INSTANCE: ApiClient? = null
        fun getInstance(): ApiClient {
            if (INSTANCE == null) {
                INSTANCE = ApiClient()
            }
            return INSTANCE!!
        }
    }
}
