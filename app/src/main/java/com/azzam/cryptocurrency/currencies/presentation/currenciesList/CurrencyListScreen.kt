package com.azzam.cryptocurrency.currencies.presentation.currenciesList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.currencies.presentation.currenciesList.components.CryptoCurrencyListItem
import com.azzam.cryptocurrency.currencies.presentation.customViews.Toolbar
import com.azzam.cryptocurrency.currencies.presentation.utils.cryptoCurrencyUiModel1
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme
import com.azzam.cryptocurrency.ui.theme.titleLarge

@Composable
fun CurrencyListScreen(
    state: CurrenciesListState,
    onAction: (CryptoCurrenciesListAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Toolbar(
            title = stringResource(R.string.coins),
            showBackButton = false,
            modifier = Modifier
                .fillMaxWidth()
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (loading, noData) = createRefs()

            when (state.isLoading) {
                true -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .constrainAs(loading) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .testTag("currencyListLoader")
                    )
                }

                false -> {
                    if (state.currencies.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_data),
                            style = titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .constrainAs(noData) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .testTag("noData")
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(0.dp))
                            }
                            itemsIndexed(state.currencies) { index, item ->
                                if (item != null) {
                                    CryptoCurrencyListItem(
                                        model = item,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("item$index")
                                    ) {
                                        onAction.invoke(
                                            CryptoCurrenciesListAction.OnCryptoCurrencyClick(
                                                item
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun CurrenciesListScreenPreview() {
    CryptoCurrencyTheme {
        CurrencyListScreen(
            state = CurrenciesListState(
                currencies = listOf(cryptoCurrencyUiModel1),
                isLoading = false
            ),
            onAction = {}
        )
    }
}