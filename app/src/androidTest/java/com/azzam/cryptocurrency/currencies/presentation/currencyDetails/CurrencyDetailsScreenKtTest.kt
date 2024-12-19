package com.azzam.cryptocurrency.currencies.presentation.currencyDetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.utils.cryptoCurrencyModel1
import org.junit.Rule
import org.junit.Test

class CurrencyDetailsScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appShouldShowLoaderWhenLoading() {
        val state = CurrencyDetailsState(
            isLoading = true,
            currencyDetails =
            cryptoCurrencyModel1.toCryptoCurrencyUiModel()
        )
        composeTestRule.setContent {
            CurrencyDetailsScreen(
                state = state,
                onAction = {}
            )
        }
        composeTestRule.onNodeWithTag("currencyDetailsLoader").assertIsDisplayed()
    }

    @Test
    fun appShouldDisplayTheDataCorrectly() {
        val model = cryptoCurrencyModel1.toCryptoCurrencyUiModel()
        val state = CurrencyDetailsState(
            isLoading = true,
            currencyDetails = model
        )
        composeTestRule.setContent {
            CurrencyDetailsScreen(
                state = state,
                onAction = {}
            )
        }

        composeTestRule.onNodeWithText(model.name.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.marketCapitalization.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.price.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.displayChangePercent.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.exchangeVolume.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.supply.toString()).assertIsDisplayed()
    }
}