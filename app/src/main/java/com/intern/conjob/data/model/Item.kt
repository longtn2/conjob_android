package com.intern.conjob.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("id") val id: String,
    @SerialName("coin_id") val coinId: Long,
    @SerialName("name") val name: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("market_cap_rank") val marketCapRank: Long,
    @SerialName("thumb") val thumb: String,
    @SerialName("small") val small: String,
    @SerialName("large") val large: String,
    @SerialName("slug") val slug: String,
    @SerialName("price_btc") val priceBtc: Double,
    @SerialName("score") val score: Long
)
