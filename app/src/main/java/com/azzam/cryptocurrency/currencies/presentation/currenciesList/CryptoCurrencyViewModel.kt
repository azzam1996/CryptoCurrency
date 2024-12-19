package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.currencies.domain.models.CryptoCurrencyModel
import com.azzam.cryptocurrency.currencies.domain.usecase.GetTopTenCryptoCurrencies
import com.azzam.cryptocurrency.currencies.domain.utils.NetworkError
import com.azzam.cryptocurrency.currencies.domain.utils.Resource
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.CurrencyDetailsAction
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.CurrencyDetailsEvent
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.CurrencyDetailsState
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModelList
import com.azzam.cryptocurrency.currencies.presentation.utils.CommonUIEvent
import com.azzam.cryptocurrency.currencies.presentation.utils.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class CryptoCurrencyViewModel @Inject constructor(private val getTopTenCryptoCurrencies: GetTopTenCryptoCurrencies) :
    ViewModel() {
    private val _currenciesListState = MutableStateFlow(CurrenciesListState())
    val currenciesListState = _currenciesListState.asStateFlow()

    private val _currenciesListEvents = Channel<CryptoCurrenciesListEvent>()
    val currenciesListEvents = _currenciesListEvents.receiveAsFlow()

    private val _currencyDetailsState = MutableStateFlow(CurrencyDetailsState())
    val currencyDetailsState = _currencyDetailsState.asStateFlow()

    private val _currencyDetailsEvents = Channel<CurrencyDetailsEvent>()
    val currencyDetailsEvents = _currencyDetailsEvents.receiveAsFlow()

    private val _commonUIEvents = Channel<CommonUIEvent>()
    val commonUIEvents = _commonUIEvents.receiveAsFlow()

    private var _selectedCryptoCurrencyId = MutableStateFlow<String?>(null)

    private var refreshJob: Job? = null

    init {
        refresh()
    }

    // Refresh every 1 minute
    private fun refresh() {
        refreshJob?.cancel()
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable, "Unhandled exception in coroutine")
        }
        refreshJob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            while (true) {
                Timber.v("Refresh .......")
                getTopTenCryptoCurrencies()
                delay(1.minutes)
            }
        }
    }

    private fun cancelRefresh() {
        refreshJob?.cancel()
    }

    // Get Top 10 Crypto Currencies from Server
    fun getTopTenCryptoCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            getTopTenCryptoCurrencies.invoke().onEach { result ->
                when (result) {
                    is Resource.Failed -> {
                        handleErrorResponse(result.errorCode, result.message)
                    }

                    is Resource.Loading -> {
                        _currenciesListState.update {
                            it.copy(isLoading = true)
                        }
                        _currencyDetailsState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        handleSuccessfulResponse(result.data)
                    }
                }

            }.launchIn(this)
        }
    }

    // Assign Crypto Currencies to Display Them in List Screen, Navigate to Details Screen
    // if User Clicked on Crypto Currency, Update Selected Crypto Currency
    private fun handleSuccessfulResponse(data: List<CryptoCurrencyModel?>?) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencies = data?.toCryptoCurrencyUiModelList()
            _currenciesListState.update {
                it.copy(
                    isLoading = false,
                    currencies = currencies ?: emptyList()
                )
            }
            _currencyDetailsState.update {
                it.copy(isLoading = false)
            }

            if (_selectedCryptoCurrencyId.value != null) {
                val selectedCryptoCurrency =
                    _currenciesListState.value.currencies.find {
                        it?.id == _selectedCryptoCurrencyId.value
                    }
                Timber.v("Selected currency $selectedCryptoCurrency")
                _selectedCryptoCurrencyId.value = null
                if (selectedCryptoCurrency == null) {
                    _commonUIEvents.send(
                        CommonUIEvent.ShowStringResource(R.string.error_currency_not_found)
                    )
                } else {
                    _currencyDetailsState.update {
                        it.copy(currencyDetails = selectedCryptoCurrency)
                    }
                    _currenciesListEvents.send(CryptoCurrenciesListEvent.NavigateToCurrencyDetails)
                }
            }

            if (_currencyDetailsState.value.currencyDetails != null) {
                val newCurrency =
                    data?.find { it?.id == _currencyDetailsState.value.currencyDetails?.id }
                _currencyDetailsState.update {
                    it.copy(currencyDetails = newCurrency?.toCryptoCurrencyUiModel())
                }
            }
        }
    }

    // Hide Loaders, Show Error Message from Server/Android Side
    private fun handleErrorResponse(error: NetworkError?, message: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.e(message)
            _currenciesListState.update {
                it.copy(isLoading = false)
            }
            _currencyDetailsState.update {
                it.copy(isLoading = false)
            }
            if (message != null) {
                _commonUIEvents.send(
                    CommonUIEvent.ShowStringMessage(message)
                )
            } else {
                val msg = error?.getErrorMessage() ?: R.string.unknown_error
                _commonUIEvents.send(
                    CommonUIEvent.ShowStringResource(msg)
                )
            }
        }
    }


    // Handle Crypto Currencies List Actions
    fun onCurrenciesListAction(action: CryptoCurrenciesListAction) {
        when (action) {
            is CryptoCurrenciesListAction.OnCryptoCurrencyClick -> {
                Timber.v("Clicked on ${action.cryptoCurrency?.name}")
                if(action.cryptoCurrency != null) {
                    _selectedCryptoCurrencyId.value = action.cryptoCurrency.id
                    refresh()
                }
            }
        }
    }

    // Handle Crypto Currency Details Actions
    fun onCurrencyDetailsAction(action: CurrencyDetailsAction) {
        when (action) {
            is CurrencyDetailsAction.OnGoBack -> {
                Timber.v("Go back")
                viewModelScope.launch(Dispatchers.IO) {
                    _currencyDetailsEvents.send(CurrencyDetailsEvent.GoBack)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelRefresh()
        Timber.v("ViewModel destroyed, resources cleaned up")
    }
}