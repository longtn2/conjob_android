package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.CoinRemoteDataSource
import com.intern.conjob.data.response.TrendingResponse
import kotlinx.coroutines.flow.Flow

class CoinRepository(private val coinRemoteDataSource: CoinRemoteDataSource) : Repository() {
    fun getTrending(): Flow<FlowResult<TrendingResponse>> = safeFlow {
        coinRemoteDataSource.getTrending()
    }
}
