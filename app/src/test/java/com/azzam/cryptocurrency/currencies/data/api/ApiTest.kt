package com.azzam.cryptocurrency.currencies.data.api

import com.azzam.cryptocurrency.commonSetup
import com.azzam.cryptocurrency.currencies.domain.utils.LIMIT
import com.azzam.cryptocurrency.errorCode
import com.azzam.cryptocurrency.readJsonFile
import com.azzam.cryptocurrency.successCode
import com.azzam.cryptocurrency.successfulServerResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.ClassRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {
    @get:ClassRule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(Api::class.java)
    }
    private val successfulResponse = readJsonFile("json/SuccessfulResponse.json")

    @BeforeEach
    fun setup() {
        commonSetup()
    }

    @Test
    fun `getCurrenciesList should use the correct endpoint`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(successCode)
                .setBody(successfulResponse)
        )

        val responseFromApi = apiService.getCurrenciesList(LIMIT)

        assertEquals(
            responseFromApi.isSuccessful,
            true
        )
        assertEquals(
            mockWebServer.takeRequest().path,
            "/assets?limit=$LIMIT"
        )
    }

    @Test
    fun `test getCurrenciesList in successful case`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(successCode)
                .setBody(successfulResponse)
        )

        val responseFromApi = apiService.getCurrenciesList(LIMIT)

        assertEquals(
            responseFromApi.code(),
            successCode
        )
        assertEquals(
            successfulServerResponse,
            responseFromApi.body()
        )
    }

    @Test
    fun `test getCurrenciesList error case`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(errorCode)
        )

        val responseFromApi = apiService.getCurrenciesList(-LIMIT)

        mockWebServer.takeRequest()
        assertEquals(
            responseFromApi.code(),
            errorCode
        )
    }
}