package com.azzam.cryptocurrency.currencies.presentation.currencyDetails

sealed interface CurrencyDetailsEvent {
    data object GoBack : CurrencyDetailsEvent
}