package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.azzam.cryptocurrency.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyListScreenKtActivityTest {
    @get:Rule
    val activityTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testIfAppLaunchedSuccessfully() {
        activityTestRule.onNodeWithText("COINS").assertIsDisplayed()
    }

    @Test
    fun clickingOnListItemShouldNavigateToDetailsScreen() {
        activityTestRule.waitUntil(timeoutMillis = 10000) {
            activityTestRule.onNodeWithTag("item0").isDisplayed()
        }
        activityTestRule.onNodeWithTag("item0").performClick()
        activityTestRule.waitUntil(timeoutMillis = 10000) {
            activityTestRule.onNodeWithTag("currencyDetails").isDisplayed()
        }
    }

    @Test
    fun clickingOnBackButtonInToolbarShouldNavigateBack() {
        activityTestRule.waitUntil(timeoutMillis = 10000) {
            activityTestRule.onNodeWithTag("item0").isDisplayed()
        }
        activityTestRule.onNodeWithTag("item0").performClick()

        activityTestRule.waitUntil(timeoutMillis = 10000) {
            activityTestRule.onNodeWithTag("currencyDetails").isDisplayed()
        }
        activityTestRule.onNodeWithTag("btnBack").performClick()
        activityTestRule.waitUntil(timeoutMillis = 10000) {
            activityTestRule.onNodeWithTag("item0").isDisplayed()
        }
    }
}