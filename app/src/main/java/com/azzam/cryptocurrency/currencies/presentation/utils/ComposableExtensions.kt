package com.azzam.cryptocurrency.currencies.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.ui.theme.Green
import com.azzam.cryptocurrency.ui.theme.Red
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext


@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifeCycleOwner) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collectLatest(onEvent)
            }
        }
    }
}

@Composable
fun getValueOrNoData(value: String?): String {
    return value ?: stringResource(id = R.string.no_data)
}

@Composable
fun getChangePercentColor(value: Double?): Color = if ((value ?: 0.0) > 0) Green else Red

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(viewModelStoreOwner = parentEntry)
}