package com.azzam.cryptocurrency.currencies.data.api.dto


import com.google.gson.annotations.SerializedName

// this file was auto generated
data class CryptoCurrencyDto(
    @SerializedName("changePercent24Hr")
    val changePercent24Hr: Double? = null,
    @SerializedName("explorer")
    val explorer: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("marketCapUsd")
    val marketCapUsd: Double? = null,
    @SerializedName("maxSupply")
    val maxSupply: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("priceUsd")
    val priceUsd: Double? = null,
    @SerializedName("rank")
    val rank: String? = null,
    @SerializedName("supply")
    val supply: Double? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: Double? = null,
    @SerializedName("vwap24Hr")
    val vWAP24Hr: Double? = null
)