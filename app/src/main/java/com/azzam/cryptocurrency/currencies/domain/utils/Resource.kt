package com.azzam.cryptocurrency.currencies.domain.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: NetworkError? = null
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?, message: String? = null) : Resource<T>(data, message)
    class Failed<T>(message: String?, data: T? = null, errorCode: NetworkError?) :
        Resource<T>(data, message, errorCode)
}