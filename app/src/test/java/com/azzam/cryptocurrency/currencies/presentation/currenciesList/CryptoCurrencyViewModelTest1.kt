package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import android.icu.text.NumberFormat
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.azzam.cryptocurrency.cryptoCurrencyModel1
import com.azzam.cryptocurrency.cryptoCurrencyModel2
import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.domain.usecase.GetTopTenCryptoCurrencies
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.Locale
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CryptoCurrencyViewModelTest1 {

    @get:ClassRule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CryptoCurrencyViewModel

    //@Mock
    lateinit var getTopTenCryptoCurrencies: GetTopTenCryptoCurrencies

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()



    @BeforeEach
    fun setup() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
        getTopTenCryptoCurrencies = mockk(relaxed = true)
        viewModel = CryptoCurrencyViewModel(getTopTenCryptoCurrencies)
    }

    @Test
    fun `r`() = runBlocking {

    }
/*
    @Test
    fun `test1`() = runTest {
        assertEquals(false, viewModel.currenciesListState.value.isLoading)
        assertEquals(emptyList<CryptoCurrencyUiModel>(), viewModel.currenciesListState.value.currencies)
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(Resource.Success(listOf(cryptoCurrencyModel1)))
        //whenever(getTopTenCryptoCurrencies.invoke()).thenReturn(flowOf(Resource.Success(listOf(cryptoCurrencyModel1))))

        val job = launch {  viewModel.getTopTenCryptoCurrencies()}
        job.join()

        testDispatcher.scheduler.advanceUntilIdle()

        verify { getTopTenCryptoCurrencies.invoke() }

        //assertEquals(listOf(cryptoCurrencyModel1), viewModel.currenciesListState.value.currencies)

        //assertEquals(true, viewModel.currenciesListState.value.isLoading)
    }


    @Test
    fun `test`() = runTest {
        assertEquals(false, viewModel.currenciesListState.value.isLoading)
        assertEquals(emptyList<CryptoCurrencyUiModel>(), viewModel.currenciesListState.value.currencies)
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(Resource.Success(listOf(cryptoCurrencyModel1)))
        //whenever(getTopTenCryptoCurrencies.invoke()).thenReturn(flowOf(Resource.Success(listOf(cryptoCurrencyModel1))))

        mockkStatic(NumberFormat::class)
        val mockNumberFormat = mockk<NumberFormat>(relaxed = true)

        // Define the behavior of the mock
        every { NumberFormat.getNumberInstance(Locale.US) } returns mockNumberFormat
        val job = launch {  viewModel.getTopTenCryptoCurrencies()}
        job.join()

        testDispatcher.scheduler.advanceUntilIdle()
        runBlocking { delay(2000) }

        //verify { getTopTenCryptoCurrencies.invoke() }

        viewModel.currenciesListState.test {
            val item = awaitItem()
            assertEquals(listOf(cryptoCurrencyModel1.toCryptoCurrencyUiModel()), item.currencies)

            cancelAndIgnoreRemainingEvents()
        }



        //assertEquals(listOf(cryptoCurrencyModel1), viewModel.currenciesListState.value.currencies)

        //assertEquals(true, viewModel.currenciesListState.value.isLoading)
    }

    @Test
    fun `test3`() = runTest {
        assertEquals(false, viewModel.currenciesListState.value.isLoading)
        assertEquals(emptyList<CryptoCurrencyUiModel>(), viewModel.currenciesListState.value.currencies)
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(Resource.Success(listOf(cryptoCurrencyModel1)))

        mockkStatic(NumberFormat::class)
        val mockNumberFormat = mockk<NumberFormat>(relaxed = true)

        every { NumberFormat.getNumberInstance(Locale.US) } returns mockNumberFormat

        viewModel.getTopTenCryptoCurrencies()

       // job.join()

        //runBlocking { delay(2000) }

        // Call the function you're testing

        // Collect the emitted value and assert
        viewModel.currenciesListState.test {
            skipItems(1)
            val item = awaitItem()
            println(item)
            if(item.currencies.isNotEmpty())
                assertEquals(listOf(cryptoCurrencyModel1.toCryptoCurrencyUiModel()), item.currencies)
            cancelAndConsumeRemainingEvents()
            ensureAllEventsConsumed()
        }


       // advanceUntilIdle()

    }

 */
}