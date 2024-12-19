package com.azzam.cryptocurrency.currencies.data.api

import android.util.Log
import com.azzam.cryptocurrency.currencies.domain.utils.NetworkError
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.azzam.cryptocurrency.errorCode
import com.azzam.cryptocurrency.readJsonFile
import com.google.gson.JsonSyntaxException
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class ApiHelperKtTest {

    @BeforeEach
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.getStackTraceString(any()) } returns "Mocked Stack Trace"
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when NoConnectivityException is thrown, return NetworkError_NO_INTERNET`() = runBlocking {
        val result = safeApiCall<Resource<Nothing>> { throw NoConnectivityException() }

        assertEquals(NetworkError.NO_INTERNET, result.errorCode)
    }

    @Test
    fun `when JsonSyntaxException is thrown, return NetworkError_JSON_PARSE_ERROR`() = runBlocking {
        val result = safeApiCall<Resource<Nothing>> { throw JsonSyntaxException("") }

        assertEquals(NetworkError.JSON_PARSE_ERROR, result.errorCode)
    }

    @Test
    fun `when Unknown Exception is thrown, return NetworkError_UNKNOWN`() = runBlocking {
        val result = safeApiCall<Resource<Nothing>> { throw Exception("") }

        assertEquals(NetworkError.UNKNOWN, result.errorCode)
    }

    @Test
    fun `test parseResponse in DAD_REQUEST case`() {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(
            errorCode,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
        val result = parseResponse(response)

        assertEquals(NetworkError.DAD_REQUEST, result.errorCode)
    }

    @Test
    fun `test parseResponse in UNAUTHORIZED case`() {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(
            401,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
        val result = parseResponse(response)

        assertEquals(NetworkError.UNAUTHORIZED, result.errorCode)
    }

    @Test
    fun `test parseResponse in NOT_FOUND case`() {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(
            404,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
        val result = parseResponse(response)

        assertEquals(NetworkError.NOT_FOUND, result.errorCode)
    }

    @Test
    fun `test parseResponse in REQUEST_TIMEOUT case`() {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(
            408,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
        val result = parseResponse(response)

        assertEquals(NetworkError.REQUEST_TIMEOUT, result.errorCode)
    }

    @Test
    fun `test parseResponse in SERVER_ERROR case`() {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(
            503,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
        val result = parseResponse(response)

        assertEquals(NetworkError.SERVER_ERROR, result.errorCode)
    }

    @Test
    fun `test parseResponse in UNKNOWN case`() {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(
            444,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
        val result = parseResponse(response)

        assertEquals(NetworkError.UNKNOWN, result.errorCode)
    }

    @Test
    fun `test parseResponse in JsonSyntaxException case`() {

        val result = parseResponse(methodCauseJsonSyntaxException())

        assertEquals(NetworkError.JSON_PARSE_ERROR, result.errorCode)
    }

    private fun methodCauseJsonSyntaxException(): Response<Nothing> {
        val mediaType = "application/json".toMediaType()
        val response = Response.error<Nothing>(444, "lorem".toResponseBody(mediaType))
        return response
    }
}