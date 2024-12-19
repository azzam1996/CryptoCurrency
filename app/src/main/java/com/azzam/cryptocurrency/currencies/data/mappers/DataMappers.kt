package com.azzam.cryptocurrency.currencies.data.mappers

import com.azzam.cryptocurrency.currencies.data.api.dto.CryptoCurrencyDto
import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel

fun CryptoCurrencyDto.toCryptoCurrencyModel(): CryptoCurrencyModel {
    return CryptoCurrencyModel(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        supply = supply,
        explorer = explorer,
        priceUsd = priceUsd,
        vWAP24Hr = vWAP24Hr,
        changePercent24Hr = changePercent24Hr,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        volumeUsd24Hr = volumeUsd24Hr
    )
}

fun List<CryptoCurrencyDto?>.toCryptoCurrencyModelList(): List<CryptoCurrencyModel?> {
    return this.map {
        it?.toCryptoCurrencyModel()
    }
}