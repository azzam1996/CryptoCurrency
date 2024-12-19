package com.azzam.cryptocurrency.currencies.data.repository

import com.azzam.cryptocurrency.currencies.data.api.Api
import com.azzam.cryptocurrency.currencies.data.api.safeApiCall
import com.azzam.cryptocurrency.currencies.data.mappers.toCryptoCurrencyModelList
import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.domain.repository.Repository
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) : Repository {
    override fun getCurrenciesList(limit: Int): Flow<Resource<List<CryptoCurrencyModel?>?>> =
        flow {
            emit(Resource.Loading())
            when (val response = safeApiCall { api.getCurrenciesList(limit) }) {
                is Resource.Success -> {
                    emit(
                        Resource.Success(
                            data = response.data?.data?.toCryptoCurrencyModelList() ?: emptyList()
                        )
                    )
                }

                else -> {
                    emit(
                        Resource.Failed(
                            message = response.message,
                            errorCode = response.errorCode
                        )
                    )
                }
            }
        }
}