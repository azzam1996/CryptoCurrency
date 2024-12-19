package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azzam.cryptocurrency.AppState
import com.azzam.cryptocurrency.currencies.presentation.navigation.Screens
import com.azzam.cryptocurrency.currencies.presentation.utils.CommonUIEvent
import com.azzam.cryptocurrency.currencies.presentation.utils.ObserveAsEvents

@Composable
fun CurrenciesListRoute(
    appState: AppState,
    viewModel: CryptoCurrencyViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.currenciesListState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.currenciesListEvents) { event ->
        when (event) {
            is CryptoCurrenciesListEvent.NavigateToCurrencyDetails -> {
                appState.navigate(Screens.CurrencyDetailsScreen.route)
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
    CurrencyListScreen(
        state = state,
        onAction = viewModel::onCurrenciesListAction,
        modifier = modifier
    )
}