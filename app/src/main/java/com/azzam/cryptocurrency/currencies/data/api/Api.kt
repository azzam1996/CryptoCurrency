package com.azzam.cryptocurrency.currencies.data.api

import com.azzam.cryptocurrency.currencies.data.api.dto.CryptoCurrencyListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {
    @GET("assets")
    suspend fun getCurrenciesList(@Query("limit") limit: Int): Response<CryptoCurrencyListDto?>
}