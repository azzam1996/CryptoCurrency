package com.azzam.cryptocurrency

import com.azzam.cryptocurrency.currencies.data.api.dto.CryptoCurrencyDto
import com.azzam.cryptocurrency.currencies.data.api.dto.CryptoCurrencyListDto
import com.azzam.cryptocurrency.currencies.data.api.dto.ErrorResponse
import com.azzam.cryptocurrency.currencies.data.mappers.toCryptoCurrencyModel
import com.google.gson.Gson


val cryptoCurrencyDtoString1 = "{\"id\":\"bitcoin\",\"rank\":\"1\",\"symbol\":\"BTC\",\"name\":\"Bitcoin\",\"supply\":\"19796937.0000000000000000\",\"maxSupply\":\"21000000.0000000000000000\",\"marketCapUsd\":\"2114723385181.7501240494645992\",\"volumeUsd24Hr\":\"20715678594.0081259396068873\",\"priceUsd\":\"106820.7362170092335016\",\"changePercent24Hr\":\"3.6830638183621513\",\"vwap24Hr\":\"104899.1838927162502964\",\"explorer\":\"https://blockchain.info/\"}"
val cryptoCurrencyDto1 = Gson().fromJson(cryptoCurrencyDtoString1, CryptoCurrencyDto::class.java)

val cryptoCurrencyModel1 = cryptoCurrencyDto1.toCryptoCurrencyModel()
val cryptoCurrencyModel2 = cryptoCurrencyModel1.copy(id = "new id")

val successfulServerResponse = Gson().fromJson(
    readJsonFile("json/SuccessfulResponse.json"),
    CryptoCurrencyListDto::class.java
)
val successfulListWithNullValuesServerResponse = successfulServerResponse.copy(data = listOf(null))
val successfulNullValueServerResponse = null
val successfulNullDataValueServerResponse = successfulServerResponse.copy(data = null)
val errorServerResponse = Gson().fromJson(
    readJsonFile("json/ErrorResponse.json"),
    ErrorResponse::class.java
)

val successCode = 200
val errorCode = 400