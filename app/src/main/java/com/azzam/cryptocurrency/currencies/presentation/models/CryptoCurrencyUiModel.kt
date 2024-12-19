package com.azzam.cryptocurrency.currencies.presentation.models

import androidx.annotation.DrawableRes

data class CryptoCurrencyUiModel(
    val id: String? = null,
    val name: String? = null,
    val symbol: String? = null,
    val price: String? = null,
    val changePercent: Double? = null,
    val displayChangePercent: String? = null,
    @DrawableRes val icon: Int? = null,
    val marketCapitalization: String? = null,
    val exchangeVolume: String? = null,
    val supply: String? = null,
)
