package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.azzam.cryptocurrency.currencies.presentation.mappers.toCryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.utils.cryptoCurrencyModel1
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CurrencyListScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appShouldShowLoaderWhenLoading() {
        val state = CurrenciesListState(
            isLoading = true,
            currencies = listOf(
                cryptoCurrencyModel1.toCryptoCurrencyUiModel(),
                cryptoCurrencyModel1.toCryptoCurrencyUiModel()
            )
        )
        composeTestRule.setContent {
            CurrencyListScreen(
                state = state,
                onAction = {}
            )
        }
        composeTestRule.onNodeWithTag("currencyListLoader").assertIsDisplayed()
    }

    @Test
    fun appShouldShowListOfItemsInCaseOfSuccessfulNotEmptyResponse() {
        val state = CurrenciesListState(
            isLoading = false,
            currencies = listOf(
                cryptoCurrencyModel1.toCryptoCurrencyUiModel(),
                cryptoCurrencyModel1.toCryptoCurrencyUiModel()
            )
        )
        composeTestRule.setContent {
            CurrencyListScreen(
                state = state,
                onAction = {}
            )
        }
        composeTestRule.onNodeWithTag("item0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("item1").assertIsDisplayed()
    }

    @Test
    fun appShouldShowNoDataInCaseOfSuccessfulEmptyResponse() {
        val state = CurrenciesListState(
            isLoading = false,
            currencies = emptyList()
        )
        composeTestRule.setContent {
            CurrencyListScreen(
                state = state,
                onAction = {}
            )
        }
        composeTestRule.onNodeWithTag("noData").assertIsDisplayed()
    }

    @Test
    fun appShouldDisplayTheDataCorrectly() {
        val model = cryptoCurrencyModel1.toCryptoCurrencyUiModel()
        val state = CurrenciesListState(
            isLoading = false,
            currencies = listOf(
                model
            )
        )
        composeTestRule.setContent {
            CurrencyListScreen(
                state = state,
                onAction = {}
            )
        }
        composeTestRule.onNodeWithText(model.name.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.symbol.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.price.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(model.displayChangePercent.toString()).assertIsDisplayed()
    }
}