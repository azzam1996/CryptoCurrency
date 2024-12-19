package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel

interface CryptoCurrenciesListAction {
    data class OnCryptoCurrencyClick(val cryptoCurrency: CryptoCurrencyUiModel?) : CryptoCurrenciesListAction
}