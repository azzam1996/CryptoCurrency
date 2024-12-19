package com.azzam.cryptocurrency.currencies.presentation.currencyDetails

import androidx.compose.runtime.Immutable
import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel

@Immutable
data class CurrencyDetailsState(
    val isLoading: Boolean = false,
    val currencyDetails: CryptoCurrencyUiModel? = null,
)