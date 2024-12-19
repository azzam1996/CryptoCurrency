package com.azzam.cryptocurrency.currencies.presentation.currencyDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.currencies.presentation.currencyDetails.components.LabelValueItem
import com.azzam.cryptocurrency.currencies.presentation.customViews.Toolbar
import com.azzam.cryptocurrency.currencies.presentation.utils.cryptoCurrencyUiModel1
import com.azzam.cryptocurrency.currencies.presentation.utils.getChangePercentColor
import com.azzam.cryptocurrency.ui.theme.Blue
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme
import com.azzam.cryptocurrency.ui.theme.WhiteWith70PercentOpacity

@Composable
fun CurrencyDetailsScreen(
    state: CurrencyDetailsState,
    onAction: (CurrencyDetailsAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val changePercentColor = getChangePercentColor(state.currencyDetails?.changePercent)

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("currencyDetails")
    ) {
        Toolbar(
            title = state.currencyDetails?.name,
            showBackButton = true,
            modifier = Modifier
                .fillMaxWidth(),
            onBackClick = {
                onAction.invoke(CurrencyDetailsAction.OnGoBack)
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(all = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {


                LabelValueItem(
                    label = stringResource(R.string.price),
                    value = state.currencyDetails?.price,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                LabelValueItem(
                    label = stringResource(R.string.change_24hr),
                    value = state.currencyDetails?.displayChangePercent,
                    valueColor = changePercentColor,
                    modifier = Modifier
                        .fillMaxWidth()
                )


                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                        .height(1.dp)
                        .background(color = Blue)
                )

                LabelValueItem(
                    label = stringResource(R.string.market_cap),
                    value = state.currencyDetails?.marketCapitalization,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                LabelValueItem(
                    label = stringResource(R.string.volume_24hr),
                    value = state.currencyDetails?.exchangeVolume,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                LabelValueItem(
                    label = stringResource(R.string.supply),
                    value = state.currencyDetails?.supply,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = WhiteWith70PercentOpacity,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .testTag("currencyDetailsLoader"),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
private fun CurrencyDetailsScreenPreview() {
    CryptoCurrencyTheme {
        CurrencyDetailsScreen(
            state = CurrencyDetailsState(
                isLoading = false,
                currencyDetails = cryptoCurrencyUiModel1
            ),
            onAction = {},
            modifier = Modifier
        )
    }
}



