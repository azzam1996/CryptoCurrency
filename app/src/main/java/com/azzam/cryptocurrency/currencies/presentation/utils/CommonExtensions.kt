package com.azzam.cryptocurrency.currencies.presentation.utils

import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.currencies.domain.utils.NetworkError


fun NetworkError.getErrorMessage(): Int {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.time_out
        NetworkError.DAD_REQUEST -> R.string.bad_request
        NetworkError.UNAUTHORIZED -> R.string.unauthorized
        NetworkError.NOT_FOUND -> R.string.unknown_error
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.SERVER_ERROR -> R.string.server_error
        NetworkError.JSON_PARSE_ERROR -> R.string.unknown_error
        NetworkError.UNKNOWN -> R.string.unknown_error
    }
}