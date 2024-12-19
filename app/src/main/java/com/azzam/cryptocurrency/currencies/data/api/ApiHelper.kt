package com.azzam.cryptocurrency.currencies.data.api

import android.util.Log
import com.azzam.cryptocurrency.currencies.data.api.dto.ErrorResponse
import com.azzam.cryptocurrency.currencies.domain.utils.NetworkError
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import java.lang.Exception
import timber.log.Timber
import kotlin.coroutines.coroutineContext



suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T?> {
    return try {
        parseResponse(apiCall.invoke())
    } catch (e: NoConnectivityException) {
        Timber.tag("Crypto Currency : ").e(Log.getStackTraceString(e))
        Resource.Failed(message = e.message, errorCode = NetworkError.NO_INTERNET)
    } catch (e: JsonSyntaxException) {
        Timber.tag("Crypto Currency : ").e(Log.getStackTraceString(e))
        Resource.Failed(message = e.message, errorCode = NetworkError.JSON_PARSE_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Timber.tag("Crypto Currency : ").e(Log.getStackTraceString(e))
        Resource.Failed(message = e.message, errorCode = NetworkError.UNKNOWN)
    }
}


internal fun <T> parseResponse(response: Response<T>): Resource<T?> {
    return if (response.isSuccessful) {
        Timber.v("Successful")
        Resource.Success(response.body())
    } else {
        var errorMessage: String? = null
        try {
            val errorResponse =
                Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            errorMessage = errorResponse.error
        } catch (e: JsonSyntaxException) {
            Timber.e("Error parsing error response: ${e.message}")
            return Resource.Failed(
                errorCode = NetworkError.JSON_PARSE_ERROR,
                message = null
            )
        }
        when (response.code()) {
            400 -> {
                Resource.Failed(
                    errorCode = NetworkError.DAD_REQUEST,
                    message = errorMessage,
                )
            }

            401 -> {
                Resource.Failed(
                    errorCode = NetworkError.UNAUTHORIZED,
                    message = errorMessage,
                )
            }

            404 -> {
                Resource.Failed(
                    errorCode = NetworkError.NOT_FOUND,
                    message = errorMessage,
                )
            }

            408 -> {
                Resource.Failed(
                    errorCode = NetworkError.REQUEST_TIMEOUT,
                    message = errorMessage,
                )
            }

            in 500..599 -> {
                Resource.Failed(
                    errorCode = NetworkError.SERVER_ERROR,
                    message = errorMessage,
                )
            }

            else -> {
                Resource.Failed(
                    errorCode = NetworkError.UNKNOWN,
                    message = errorMessage,
                )
            }
        }
    }
}
