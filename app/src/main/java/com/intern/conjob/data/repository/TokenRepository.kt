package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.TokenRemoteDataSource
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.response.TokenResponse
import kotlinx.coroutines.flow.Flow

class TokenRepository(private val tokenRemoteDataSource: TokenRemoteDataSource) : Repository() {
    fun refreshToken(token: Token): Flow<FlowResult<TokenResponse>> = safeFlow {
        tokenRemoteDataSource.refreshToken(token)
    }
}
