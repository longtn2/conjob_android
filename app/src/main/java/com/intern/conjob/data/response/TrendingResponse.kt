package com.intern.conjob.data.response

import com.intern.conjob.data.model.Coin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingResponse(@SerialName("coins") val coins: List<Coin>)
