package com.azzam.cryptocurrency.currencies.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.azzam.cryptocurrency.AppState
import com.azzam.cryptocurrency.currencies.presentation.currenciesList.CryptoCurrencyViewModel
import com.azzam.cryptocurrency.currencies.presentation.currenciesList.CurrenciesListRoute
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.CurrencyDetailsRoute
import com.azzam.cryptocurrency.currencies.presentation.utils.MAIN_GRAPH_ROUTE
import com.azzam.cryptocurrency.currencies.presentation.utils.sharedViewModel

@Composable
fun Navigation(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        NavHost(
            navController = appState.navController,
            startDestination = Screens.CurrenciesListScreen.route,
            route = MAIN_GRAPH_ROUTE
        ) {
            composable(Screens.CurrenciesListScreen.route) { entry ->
                val viewModel = entry.sharedViewModel<CryptoCurrencyViewModel>(appState.navController)
                CurrenciesListRoute(
                    appState = appState,
                    viewModel = viewModel
                )
            }
            composable(Screens.CurrencyDetailsScreen.route) { entry ->
                val viewModel = entry.sharedViewModel<CryptoCurrencyViewModel>(appState.navController)
                CurrencyDetailsRoute(
                    appState = appState,
                    viewModel = viewModel
                )
            }
        }
    }
}