package com.azzam.cryptocurrency.currencies.presentation.currenciesList


sealed interface CryptoCurrenciesListEvent {
    data object NavigateToCurrencyDetails : CryptoCurrenciesListEvent
}