package com.azzam.cryptocurrency.currencies.presentation.utils

import com.azzam.cryptocurrency.currencies.data.api.dto.CryptoCurrencyDto
import com.azzam.cryptocurrency.currencies.data.mappers.toCryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.google.gson.Gson

/*
*
* this file only to get hardcoded data for testing, preview
* */

val cryptoCurrencyDtoString1 = "{\"id\":\"bitcoin\",\"rank\":\"1\",\"symbol\":\"BTC\",\"name\":\"Bitcoin\",\"supply\":\"19796937.0000000000000000\",\"maxSupply\":\"21000000.0000000000000000\",\"marketCapUsd\":\"2114723385181.7501240494645992\",\"volumeUsd24Hr\":\"20715678594.0081259396068873\",\"priceUsd\":\"106820.7362170092335016\",\"changePercent24Hr\":\"3.6830638183621513\",\"vwap24Hr\":\"104899.1838927162502964\",\"explorer\":\"https://blockchain.info/\"}"
val cryptoCurrencyDto1 = Gson().fromJson(cryptoCurrencyDtoString1, CryptoCurrencyDto::class.java)

val cryptoCurrencyModel1 = cryptoCurrencyDto1.toCryptoCurrencyModel()
val cryptoCurrencyUiModel1 = cryptoCurrencyModel1.toCryptoCurrencyUiModel()