package com.azzam.cryptocurrency.currencies.domain.models


data class CryptoCurrencyModel(
    val changePercent24Hr: Double? = null,
    val explorer: String? = null,
    val id: String? = null,
    val marketCapUsd: Double? = null,
    val maxSupply: String? = null,
    val name: String? = null,
    val priceUsd: Double? = null,
    val rank: String? = null,
    val supply: Double? = null,
    val symbol: String? = null,
    val volumeUsd24Hr: Double? = null,
    val vWAP24Hr: Double? = null
)
