package com.azzam.cryptocurrency.currencies.domain.usecase

import app.cash.turbine.test
import com.azzam.cryptocurrency.currencies.data.mappers.toCryptoCurrencyModelList
import com.azzam.cryptocurrency.currencies.domain.repository.Repository
import com.azzam.cryptocurrency.currencies.domain.utils.LIMIT
import com.azzam.cryptocurrency.currencies.domain.utils.NetworkError
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.azzam.cryptocurrency.errorServerResponse
import com.azzam.cryptocurrency.successfulServerResponse
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GetTopTenCryptoCurrenciesTest {
    private lateinit var repository: Repository
    private lateinit var getTopTenCryptoCurrencies: GetTopTenCryptoCurrencies


    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        getTopTenCryptoCurrencies = GetTopTenCryptoCurrencies(repository)
    }

    @Test
    fun `invoke() should call getCurrenciesList on repository`() = runBlocking {
        getTopTenCryptoCurrencies.invoke().test {
            cancelAndConsumeRemainingEvents()
            ensureAllEventsConsumed()
        }

        verify { repository.getCurrenciesList(LIMIT) }
    }

    @Test
    fun `invoke() should emit loading as first emission`() = runBlocking {
        setupSuccessfulCase()
        getTopTenCryptoCurrencies.invoke().test {
            val emission = awaitItem()
            assertTrue(emission is Resource.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `invoke() should emit list of models in successful case`() = runBlocking {
        setupSuccessfulCase()
        getTopTenCryptoCurrencies.invoke().test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            assertTrue(item is Resource.Success)
            assertEquals(successfulServerResponse.data?.toCryptoCurrencyModelList(), item.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `invoke() should emit error in failed case`() = runBlocking {
        setupFailedCase()
        getTopTenCryptoCurrencies.invoke().test {
            // Skip the initial loading emission
            skipItems(1)
            val item = awaitItem()
            assertTrue(item is Resource.Failed)
            assertEquals(errorServerResponse.error, item.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun setupSuccessfulCase() {
        coEvery { repository.getCurrenciesList(LIMIT) } returns flowOf(
            Resource.Loading(),
            Resource.Success(data = successfulServerResponse.data?.toCryptoCurrencyModelList())
        )
    }

    private fun setupFailedCase() {
        coEvery { repository.getCurrenciesList(LIMIT) } returns flowOf(
            Resource.Loading(),
            Resource.Failed(
                message = errorServerResponse.error,
                errorCode = NetworkError.DAD_REQUEST
            )
        )
    }
}