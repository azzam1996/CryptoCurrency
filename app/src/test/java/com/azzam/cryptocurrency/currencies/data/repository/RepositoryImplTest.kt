package com.azzam.cryptocurrency.currencies.data.repository

import app.cash.turbine.test
import com.azzam.cryptocurrency.currencies.data.api.Api
import com.azzam.cryptocurrency.currencies.data.mappers.toCryptoCurrencyModelList
import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.domain.utils.LIMIT
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.azzam.cryptocurrency.errorCode
import com.azzam.cryptocurrency.errorServerResponse
import com.azzam.cryptocurrency.readJsonFile
import com.azzam.cryptocurrency.successfulListWithNullValuesServerResponse
import com.azzam.cryptocurrency.successfulNullDataValueServerResponse
import com.azzam.cryptocurrency.successfulNullValueServerResponse
import com.azzam.cryptocurrency.successfulServerResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    private lateinit var api: Api
    private lateinit var repository: RepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = mockk(relaxed = true)
        repository = RepositoryImpl(api)
    }

    @AfterEach
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `call getCurrenciesList on repository should call getCurrenciesList on api`() = runBlocking {
        repository.getCurrenciesList(LIMIT).test {
            cancelAndConsumeRemainingEvents()
            ensureAllEventsConsumed()
        }
        coVerify { api.getCurrenciesList(LIMIT) }
    }

    @Test
    fun `getCurrenciesList should emit loading as first emission`() = runBlocking {
        repository.getCurrenciesList(LIMIT).test {
            val emission = awaitItem()
            assertTrue(emission is Resource.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCurrenciesList should emit list of models when api call is successful`() = runBlocking {
        setupSuccessfulResponse()
        repository.getCurrenciesList(LIMIT).test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            assertTrue(item is Resource.Success)
            assertEquals(successfulServerResponse.data?.toCryptoCurrencyModelList(), item.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCurrenciesList should handle null values in List when api call is successful`() = runBlocking {
        setupSuccessfulListWithNullValueResponse()
        repository.getCurrenciesList(LIMIT).test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            assertTrue(item is Resource.Success)
            assertEquals(successfulListWithNullValuesServerResponse.data?.toCryptoCurrencyModelList(), item.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCurrenciesList should handle null response and return empty list when api call is successful`() = runBlocking {
        setupSuccessfulNullValueResponse()
        repository.getCurrenciesList(LIMIT).test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            println(item.data)
            assertTrue(item is Resource.Success)
            assertEquals(emptyList<CryptoCurrencyModel>(), item.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCurrenciesList should handle null data field in response and return empty list when api call is successful`() = runBlocking {
        setupSuccessfulNullDataValueResponse()
        repository.getCurrenciesList(LIMIT).test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            println(item.data)
            assertTrue(item is Resource.Success)
            assertEquals(emptyList<CryptoCurrencyModel>(), item.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCurrenciesList should emit error when api call is failed`() = runBlocking {
        setupErrorResponse()
        repository.getCurrenciesList(LIMIT).test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            assertTrue(item is Resource.Failed)
            assertEquals(errorServerResponse.error, item.message)
            cancelAndConsumeRemainingEvents()
        }
    }


    private fun setupSuccessfulResponse() {
        coEvery { api.getCurrenciesList(LIMIT) } returns Response.success(successfulServerResponse)
    }
    private fun setupSuccessfulListWithNullValueResponse() {
        coEvery { api.getCurrenciesList(LIMIT) } returns Response.success(successfulListWithNullValuesServerResponse)
    }
    private fun setupSuccessfulNullValueResponse() {
        coEvery { api.getCurrenciesList(LIMIT) } returns Response.success(successfulNullValueServerResponse)
    }
    private fun setupSuccessfulNullDataValueResponse() {
        coEvery { api.getCurrenciesList(LIMIT) } returns Response.success(successfulNullDataValueServerResponse)
    }
    private fun setupErrorResponse() {
        val mediaType = "application/json".toMediaType()
        coEvery { api.getCurrenciesList(LIMIT) } returns Response.error(
            errorCode,
            readJsonFile("json/ErrorResponse.json").toResponseBody(mediaType)
        )
    }
}