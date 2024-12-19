package com.azzam.cryptocurrency.currencies.domain.usecase

import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.domain.repository.Repository
import com.azzam.cryptocurrency.currencies.domain.utils.LIMIT
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetTopTenCryptoCurrencies(private val repository: Repository) {
    operator fun invoke(): Flow<Resource<List<CryptoCurrencyModel?>?>> {
        return repository.getCurrenciesList(LIMIT)
    }
}