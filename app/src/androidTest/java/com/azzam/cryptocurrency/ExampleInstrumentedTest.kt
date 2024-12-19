package com.azzam.cryptocurrency

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.azzam.cryptocurrency.currencies.presentation.currenciesList.CurrenciesListState
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.utils.cryptoCurrencyModel1

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.azzam.cryptocurrency", appContext.packageName)
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testIfItemIsVisible() {
       val state = CurrenciesListState(
           currencies = listOf(
               cryptoCurrencyModel1.toCryptoCurrencyUiModel(),
               cryptoCurrencyModel1.toCryptoCurrencyUiModel()
           )
       )

        // Set the composable content for testing
        /*
        composeTestRule.setContent {
            CurrencyListScreen(
                appState = rememberAppState(),
                state = state,
                onAction = {}
            )
        }

         */

        // Check if "Item 2" is visible
        composeTestRule.onNodeWithText("COINS").assertIsDisplayed()
    }
}