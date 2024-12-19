package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.azzam.cryptocurrency.MainDispatcherRule
import com.azzam.cryptocurrency.commonSetup
import com.azzam.cryptocurrency.cryptoCurrencyModel1
import com.azzam.cryptocurrency.currencies.data.mappers.toCryptoCurrencyModelList
import com.azzam.cryptocurrency.currencies.domain.usecase.GetTopTenCryptoCurrencies
import com.azzam.cryptocurrency.currencies.domain.utils.NetworkError
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.CurrencyDetailsAction
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.CurrencyDetailsEvent
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModelList
import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.utils.CommonUIEvent
import com.azzam.cryptocurrency.errorServerResponse
import com.azzam.cryptocurrency.successfulServerResponse
import io.mockk.clearAllMocks
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Assertions.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.ClassRule
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.minutes

class CryptoCurrencyViewModelTest {

    @get:ClassRule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CryptoCurrencyViewModel

    private lateinit var getTopTenCryptoCurrencies: GetTopTenCryptoCurrencies

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()


    @get:Rule
    val dispatcherRule = MainDispatcherRule(testDispatcher)

    @BeforeEach
    fun setup() {
        commonSetup()
        getTopTenCryptoCurrencies = mockk(relaxed = true)
        viewModel = CryptoCurrencyViewModel(getTopTenCryptoCurrencies)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `call getTopTenCryptoCurrencies in viewModel should call invoke() in getTopTenCryptoCurrencies`() =
        runBlocking {
            setupSuccessfulCase()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.currenciesListState.test {
                // Skip the initial loading emission
                skipItems(1)
                awaitItem()
                verify { getTopTenCryptoCurrencies.invoke() }
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `call getTopTenCryptoCurrencies should start loading in currenciesListState`() = runBlocking {
        setupSuccessfulCase()
        assertEquals(false, viewModel.currenciesListState.value.isLoading)
        viewModel.getTopTenCryptoCurrencies()

        viewModel.currenciesListState.test {
            //skipItems(1)
            val item = awaitItem()
            assertEquals(true, item.isLoading)
            cancelAndConsumeRemainingEvents()
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `call getTopTenCryptoCurrencies should start loading in currencyDetailsState`() =
        runBlocking {
            setupSuccessfulCase()
            assertEquals(false, viewModel.currencyDetailsState.value.isLoading)
            viewModel.getTopTenCryptoCurrencies()

            viewModel.currencyDetailsState.test {
                val item = awaitItem()
                assertEquals(true, item.isLoading)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `call getTopTenCryptoCurrencies should stop loading and update the models list in successful case`() =
        runBlocking {
            setupSuccessfulCase()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.currenciesListState.test {
                // Skip the initial loading emission
                skipItems(1)
                val item = awaitItem()
                assertEquals(false, item.isLoading)
                assertEquals(
                    successfulServerResponse.data?.toCryptoCurrencyModelList()
                        ?.toCryptoCurrencyUiModelList(),
                    item.currencies
                )
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `call getTopTenCryptoCurrencies should stop loading and emit empty list when data is null`() =
        runBlocking {
            setupSuccessfulNullDataCase()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.currenciesListState.test() {
                awaitItem()
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
            assertEquals(false, viewModel.currenciesListState.value.isLoading)
            assertEquals(
                emptyList<CryptoCurrencyUiModel>(),
                viewModel.currenciesListState.value.currencies
            )
        }

    @Test
    fun `call getTopTenCryptoCurrencies should stop loading and emit list of nulls when data is list of nulls`() =
        runBlocking {
            setupSuccessfulListOfNullsCase()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.currenciesListState.test() {
                awaitItem()
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
            assertEquals(false, viewModel.currenciesListState.value.isLoading)
            assertEquals(
                listOf<CryptoCurrencyUiModel?>(null),
                viewModel.currenciesListState.value.currencies
            )
        }


    @Test
    fun `call getTopTenCryptoCurrencies should send an error of type ShowStringMessage if message is not Empty in failed case`() =
        runBlocking {
            setupFailedCase()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.commonUIEvents.test {
                val item = awaitItem()
                assertTrue(item is CommonUIEvent.ShowStringMessage)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `call getTopTenCryptoCurrencies should send an error of type ShowStringResource in failed case if error message is empty and error is not null`() =
        runBlocking {
            setupFailedCaseWithNullMessage()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.commonUIEvents.test {
                val item = awaitItem()
                assertTrue(item is CommonUIEvent.ShowStringResource)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `call getTopTenCryptoCurrencies should send an error of type ShowStringResource in failed case if error message is empty and error is null`() =
        runBlocking {
            setupFailedCaseWithNullMessageAndNullError()
            viewModel.getTopTenCryptoCurrencies()

            viewModel.commonUIEvents.test {
                val item = awaitItem()
                assertTrue(item is CommonUIEvent.ShowStringResource)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `call onCurrenciesListAction should call invoke() in getTopTenCryptoCurrencies and save the id of the model`() =
        runBlocking {
            setupSuccessfulCase()
            viewModel.onCurrenciesListAction(
                CryptoCurrenciesListAction.OnCryptoCurrencyClick(
                    cryptoCurrencyModel1.toCryptoCurrencyUiModel()
                )
            )
            val privateField =
                CryptoCurrencyViewModel::class.java.getDeclaredField("_selectedCryptoCurrencyId")
            privateField.isAccessible = true
            val selectedCryptoCurrencyId = privateField.get(viewModel) as? MutableStateFlow<String>
            assertEquals(cryptoCurrencyModel1.id, selectedCryptoCurrencyId?.value)
            //first call is when creating the viewModel, second call is when clicking on an item
            verify(exactly = 2) { getTopTenCryptoCurrencies.invoke() }
        }

    @Test
    fun `call onCurrenciesListAction with null cryptoCurrency should not call invoke() in getTopTenCryptoCurrencies and not save the id of the model`() =
        runBlocking {
            viewModel.onCurrenciesListAction(
                CryptoCurrenciesListAction.OnCryptoCurrencyClick(
                    null
                )
            )
            val privateField =
                CryptoCurrencyViewModel::class.java.getDeclaredField("_selectedCryptoCurrencyId")
            privateField.isAccessible = true
            val selectedCryptoCurrencyId = privateField.get(viewModel) as? MutableStateFlow<String>
            assertEquals(null, selectedCryptoCurrencyId?.value)
            //first call is when creating the viewModel
            verify(exactly = 1) { getTopTenCryptoCurrencies.invoke() }
        }

    @Test
    fun `in case of successful case and item is already pressed should send navigation event`() =
        runBlocking {
            setupSuccessfulCase()

            viewModel.onCurrenciesListAction(
                CryptoCurrenciesListAction.OnCryptoCurrencyClick(
                    cryptoCurrencyModel1.toCryptoCurrencyUiModel()
                )
            )

            viewModel.currenciesListEvents.test {
                val item = awaitItem()
                assertTrue(item is CryptoCurrenciesListEvent.NavigateToCurrencyDetails)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `in case of successful case and item is already pressed and dose not exist anymore(maybe because not in top ten anymore) should send Error event`() =
        runBlocking {
            setupSuccessfulCase()

            viewModel.onCurrenciesListAction(
                CryptoCurrenciesListAction.OnCryptoCurrencyClick(
                    cryptoCurrencyModel1.toCryptoCurrencyUiModel().copy(id = "Not exist")
                )
            )

            viewModel.commonUIEvents.test {
                val item = awaitItem()
                assertTrue(item is CommonUIEvent.ShowStringResource)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `in case of successful case and item is already pressed and dose not exist anymore because we received list of null should send Error event`() =
        runBlocking {
            setupSuccessfulListOfNullsCase()

            viewModel.onCurrenciesListAction(
                CryptoCurrenciesListAction.OnCryptoCurrencyClick(
                    cryptoCurrencyModel1.toCryptoCurrencyUiModel().copy(id = "Not exist")
                )
            )

            viewModel.commonUIEvents.test {
                val item = awaitItem()
                assertTrue(item is CommonUIEvent.ShowStringResource)
                cancelAndConsumeRemainingEvents()
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `cancelRefresh should cancel the refresh job`() = runBlocking {
        val privateField = CryptoCurrencyViewModel::class.java.getDeclaredField("refreshJob")
        privateField.isAccessible = true
        val refreshJob = privateField.get(viewModel) as? Job

        val refreshMethod = CryptoCurrencyViewModel::class.java.getDeclaredMethod("refresh")
        refreshMethod.isAccessible = true
        refreshMethod.invoke(viewModel)

        val cancelRefreshMethod =
            CryptoCurrencyViewModel::class.java.getDeclaredMethod("cancelRefresh")
        cancelRefreshMethod.isAccessible = true
        cancelRefreshMethod.invoke(viewModel)

        assertEquals(true, refreshJob?.isCancelled)

    }

    @Test
    fun `OnGoBack Action in CryptoCurrencyScreen should send GoBack event`() = runBlocking {
        viewModel.onCurrencyDetailsAction(CurrencyDetailsAction.OnGoBack)
        viewModel.currencyDetailsEvents.test {
            val item = awaitItem()
            assertTrue(item is CurrencyDetailsEvent.GoBack)
            cancelAndConsumeRemainingEvents()
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `onCleared method should cancel the refresh job`() = runBlocking {
        setupSuccessfulCase()

        val refreshMethod = CryptoCurrencyViewModel::class.java.getDeclaredMethod("refresh")
        refreshMethod.isAccessible = true
        refreshMethod.invoke(viewModel)

        val privateField = CryptoCurrencyViewModel::class.java.getDeclaredField("refreshJob")
        privateField.isAccessible = true
        val refreshJob = privateField.get(viewModel) as? Job

        assertEquals(false, refreshJob?.isCancelled)

        val onClearedMethod = CryptoCurrencyViewModel::class.java.getDeclaredMethod("onCleared")
        onClearedMethod.isAccessible = true
        onClearedMethod.invoke(viewModel)
        assertEquals(true, refreshJob?.isCancelled)
    }


    /*
    * to check if refresh function is keep updating every 1 min, by checking
    * the value of loading in currenciesListState, the first 2 values should be
    * (true,false), if the function is working properly then there must be a third
    * value equal to true
    * */
    @Test
    fun `refresh function should keep updating every 1 min`() = runBlocking {
        setupSuccessfulCase()
        val refreshMethod = CryptoCurrencyViewModel::class.java.getDeclaredMethod("refresh")
        refreshMethod.isAccessible = true
        refreshMethod.invoke(viewModel)

        val timeoutDuration = 1.minutes
        viewModel.currenciesListState.test(timeout = timeoutDuration) {
            for (i in 1..3) {
                val item = awaitItem()
                println(item.isLoading)
                if (i == 3) {
                    assertEquals(true, item.isLoading)
                }
            }
            cancel()
        }
    }


    private fun setupSuccessfulCase() {
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(
            Resource.Loading(),
            Resource.Success(
                successfulServerResponse.data?.toCryptoCurrencyModelList()
            )
        )
    }

    private fun setupSuccessfulNullDataCase() {
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(
            Resource.Loading(),
            Resource.Success(
                data = null
            )
        )
    }

    private fun setupSuccessfulListOfNullsCase() {
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(
            Resource.Loading(),
            Resource.Success(
                data = listOf(null)
            )
        )
    }

    private fun setupFailedCase() {
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(
            Resource.Loading(),
            Resource.Failed(
                errorCode = NetworkError.DAD_REQUEST,
                message = errorServerResponse.error
            )
        )
    }

    private fun setupFailedCaseWithNullMessage() {
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(
            Resource.Loading(),
            Resource.Failed(
                errorCode = NetworkError.DAD_REQUEST,
                message = null
            )
        )
    }

    private fun setupFailedCaseWithNullMessageAndNullError() {
        every { getTopTenCryptoCurrencies.invoke() } returns flowOf(
            Resource.Loading(),
            Resource.Failed(
                errorCode = null,
                message = null
            )
        )
    }
}