package com.intern.conjob.ui.coin

import androidx.lifecycle.bindCommonError
import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.data.datasource.CoinRemoteDataSource
import com.intern.conjob.data.model.Coin
import com.intern.conjob.data.repository.CoinRepository
import com.intern.conjob.data.response.TrendingResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoinViewModel(private val coinRepository: CoinRepository = CoinRepository(CoinRemoteDataSource.getInstance())) :
    BaseViewModel() {
    private val _coins: MutableStateFlow<List<Coin>> = MutableStateFlow(listOf())
    var coins: StateFlow<List<Coin>> = _coins

    fun getTrending(): Flow<FlowResult<TrendingResponse>> =
        coinRepository.getTrending().bindLoading(this)
            .bindCommonError(this)
            .onSuccess {
                _coins.value = it.coins
            }
}
