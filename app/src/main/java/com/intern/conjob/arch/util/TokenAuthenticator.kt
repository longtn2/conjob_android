package com.intern.conjob.arch.util

import com.intern.conjob.data.datasource.TokenRemoteDataSource
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.repository.TokenRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {

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
        const val REFRESH = "auth/refresh"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request
        return if (request.url.toString().contains(REFRESH)) {
            null
        } else {
            authenticateToken(request)
        }
    }

    private fun authenticateToken(request: Request): Request? {
        return synchronized(this) {
            SharedPref.getToken()?.let { savedToken ->
                val currentToken = request.header(AUTHORIZATION)?.let {
                    if (BEARER.length < it.length) {
                        it.subSequence(BEARER.length, it.length)
                    } else {
                        return@synchronized null
                    }
                }

                if (currentToken != savedToken) {
                    return@synchronized getNewRequest(request, savedToken)
                }
            }

            SharedPref.getRefreshToken()?.let {
                if (tokenRepository == null) {
                    tokenRepository = TokenRepository(TokenRemoteDataSource.getInstance())
                }

                try {
                    tokenRepository?.refreshToken(Token(it))?.data?.token?.let { token ->
                        SharedPref.saveToken(token)
                        return@synchronized getNewRequest(request, token)
                    }
                } catch (e: ErrorModel.Http.ApiError) {
                    return@synchronized null
                }
            }

            return@synchronized null
        }
    }

    private fun getNewRequest(request: Request, token: String): Request {
        return request.newBuilder()
            .header(AUTHORIZATION, BEARER + token)
            .build()
    }
}
