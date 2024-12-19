package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import androidx.compose.runtime.Immutable
import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel

@Immutable
data class CurrenciesListState(
    val isLoading: Boolean = false,
    val currencies: List<CryptoCurrencyUiModel?> = emptyList(),
)
