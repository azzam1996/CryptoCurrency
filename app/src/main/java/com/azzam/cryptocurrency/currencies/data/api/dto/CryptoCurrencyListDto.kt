package com.azzam.cryptocurrency.currencies.data.api.dto


import com.google.gson.annotations.SerializedName

data class CryptoCurrencyListDto(
    @SerializedName("data")
    val `data`: List<CryptoCurrencyDto?>?,
    @SerializedName("timestamp")
    val timestamp: Long?
)