package com.azzam.cryptocurrency.currencies.presentation.mappers

import android.icu.text.NumberFormat
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel
import java.util.Locale

fun CryptoCurrencyModel.toCryptoCurrencyUiModel(): CryptoCurrencyUiModel {
    return CryptoCurrencyUiModel(
        id = id,
        name = name,
        symbol = symbol,
        price = priceUsd?.toFormattedNumber(showDollarSign = true),
        changePercent = changePercent24Hr,
        displayChangePercent = changePercent24Hr?.toFormattedPercentage(),
        marketCapitalization = marketCapUsd?.toFormattedNumber(),
        exchangeVolume = volumeUsd24Hr?.toFormattedNumber(),
        supply = supply?.toFormattedNumber(),
        icon = getCryptoCurrencyUiModelImage(symbol)
    )
}

fun List<CryptoCurrencyModel?>.toCryptoCurrencyUiModelList(): List<CryptoCurrencyUiModel?> {
    return this.map { it?.toCryptoCurrencyUiModel() }
}


fun getCryptoCurrencyUiModelImage(symbol: String?): Int {
    return when (symbol?.uppercase()) {
        "BTC" -> R.drawable.ic_btc
        "ADA" -> R.drawable.ic_ada
        "BNB" -> R.drawable.ic_bnb
        "USDT" -> R.drawable.ic_usdt
        "XRP" -> R.drawable.ic_xrp
        "AVAX" -> R.drawable.ic_avax
        "MATIC" -> R.drawable.ic_matic
        "ETH" -> R.drawable.ic_eth
        else -> R.drawable.ic_placeholder
    }
}

fun Double.toFormattedNumber(showDollarSign: Boolean = false): String {
    val suffix = when {
        this >= 1_000_000_000 -> "B" to 1_000_000_000
        this >= 1_000_000 -> "M" to 1_000_000
        this >= 1_000 -> "K" to 1_000
        else -> return if (showDollarSign) {
            "$${NumberFormat.getNumberInstance(Locale.US).format(this)}"
        } else {
            NumberFormat.getNumberInstance(Locale.US).format(this)
        }
    }

    val formattedNumber = this / suffix.second
    val numberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
        maximumFractionDigits = 2
    }

    return if (showDollarSign) {
        "$${numberFormat.format(formattedNumber)}${suffix.first}"
    } else {
        "${numberFormat.format(formattedNumber)}${suffix.first}"
    }
}

fun Double.toFormattedPercentage(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return "${formatter.format(this)}%"
}