package com.azzam.cryptocurrency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.azzam.cryptocurrency.currencies.domain.usecase.GetTopTenCryptoCurrencies
import com.azzam.cryptocurrency.currencies.presentation.navigation.Navigation
import com.azzam.cryptocurrency.ui.theme.BlueWith20PercentOpacity
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appState: AppState

    @Inject
    lateinit var getTopTenCryptoCurrencies: GetTopTenCryptoCurrencies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fullScreen()

        setContent {
            appState = rememberAppState()
            CryptoCurrencyTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) { innerPadding ->
                    Navigation(
                        appState = appState,
                        modifier = Modifier
                            .background(BlueWith20PercentOpacity)
                            .fillMaxSize()
                            .padding(bottom = innerPadding.calculateBottomPadding())
                    )
                }
            }
        }
    }
}

fun ComponentActivity.fullScreen() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}