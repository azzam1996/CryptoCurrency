package com.azzam.cryptocurrency.currencies.presentation.currencyDetails

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azzam.cryptocurrency.AppState
import com.azzam.cryptocurrency.currencies.presentation.currenciesList.CryptoCurrencyViewModel
import com.azzam.cryptocurrency.currencies.presentation.utils.CommonUIEvent
import com.azzam.cryptocurrency.currencies.presentation.utils.ObserveAsEvents

@Composable
fun CurrencyDetailsRoute(
    appState: AppState,
    viewModel: CryptoCurrencyViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.currencyDetailsState.collectAsStateWithLifecycle()


    ObserveAsEvents(viewModel.currencyDetailsEvents) { event ->
        when(event){
            CurrencyDetailsEvent.GoBack -> {
                appState.goBack()
            }
        }
    }
    ObserveAsEvents(viewModel.commonUIEvents) { event ->
        when (event) {
            is CommonUIEvent.ShowStringMessage -> {
                Toast.makeText(appState.context, event.message, Toast.LENGTH_SHORT).show()
            }

            is CommonUIEvent.ShowStringResource -> {
                Toast.makeText(appState.context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    CurrencyDetailsScreen(
        state = state,
        onAction = viewModel::onCurrencyDetailsAction,
        modifier = modifier
    )
}