package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.data.datasource.TokenRemoteDataSource
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.TokenResponse
import kotlinx.coroutines.runBlocking

class TokenRepository(private val tokenRemoteDataSource: TokenRemoteDataSource) : Repository() {
    fun refreshToken(token: Token): BaseDataResponse<TokenResponse> = runBlocking {
        tokenRemoteDataSource.refreshToken(token)
    }
}
