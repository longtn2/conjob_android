package com.intern.conjob.arch.util

import com.intern.conjob.data.datasource.TokenRemoteDataSource
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.repository.TokenRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator: Authenticator {

    companion object {
        private var tokenRepository: TokenRepository? = null
        private var INSTANCE: TokenAuthenticator? = null
        fun getInstance(): TokenAuthenticator {
            if (INSTANCE == null) {
                INSTANCE = TokenAuthenticator()
            }
            return INSTANCE!!
        }

        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "
        const val RETRY_COUNT = "RetryCount"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request
        val retryCount = retryCount(request)
        return synchronized(this) {
            if (retryCount > 2) return@synchronized null

            SharedPref.getToken()?.let { savedToken ->
                val currentToken = request.header(AUTHORIZATION)?.let {
                    it.subSequence(BEARER.length, it.length)
                }

                if (currentToken != savedToken) {
                    return@synchronized getNewRequest(request, retryCount + 1, savedToken)
                }
            }

            SharedPref.getRefreshToken()?.let {
                if (tokenRepository == null) {
                    tokenRepository = TokenRepository(TokenRemoteDataSource.getInstance())
                }

                try {
                    tokenRepository!!.refreshToken(Token(it)).data?.token?.let { token ->
                        SharedPref.saveToken(token)
                        return@synchronized getNewRequest(request, retryCount + 1, token)
                    }
                } catch (e: ErrorModel.Http.ApiError) {
                    return@synchronized null
                }
            }

            return@synchronized null
        }
    }

    private fun retryCount(request: Request): Int = request.header(RETRY_COUNT)?.toInt() ?: 0

    private fun getNewRequest(request: Request, retryCount: Int, token: String): Request {
        return request.newBuilder()
            .header(AUTHORIZATION, BEARER + token)
            .header(RETRY_COUNT, "$retryCount")
            .build()
    }
}
