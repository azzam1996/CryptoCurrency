package com.azzam.cryptocurrency.currencies.domain.repository

import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getCurrenciesList(limit: Int): Flow<Resource<List<CryptoCurrencyModel?>?>>
}