package com.intern.conjob.arch.util

import com.intern.conjob.data.datasource.TokenRemoteDataSource
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
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request
        val retryCount = retryCount(request)
        return synchronized(this) {
            if (retryCount > 2) return null

            SharedPref.getToken()?.let { savedToken ->
                val currentToken = request.header("Authorization")
                currentToken?.subSequence("Bearer ".length, currentToken.length)

                if (currentToken != savedToken) {
                    getNewRequest(request, retryCount + 1, savedToken)
                }
            }

            SharedPref.getRefreshToken()?.let {
                if (tokenRepository == null) {
                    tokenRepository = TokenRepository(TokenRemoteDataSource.getInstance())
                }
                tokenRepository!!.refreshToken(Token(it)).data?.token?.let { token ->
                        getNewRequest(request, retryCount + 1, token)
                }
            }

            null
        }
    }

    private fun retryCount(request: Request): Int = request.header("RetryCount")?.toInt() ?: 0

    private fun getNewRequest(request: Request, retryCount: Int, token: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("RetryCount", "$retryCount")
            .build()
    }
}
