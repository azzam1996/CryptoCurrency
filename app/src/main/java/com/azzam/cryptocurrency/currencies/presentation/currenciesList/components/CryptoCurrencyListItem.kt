package com.azzam.cryptocurrency.currencies.presentation.currenciesList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.azzam.cryptocurrency.currencies.presentation.models.CryptoCurrencyUiModel
import com.azzam.cryptocurrency.currencies.presentation.utils.cryptoCurrencyUiModel1
import com.azzam.cryptocurrency.currencies.presentation.utils.getChangePercentColor
import com.azzam.cryptocurrency.currencies.presentation.utils.getValueOrNoData
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme
import com.azzam.cryptocurrency.ui.theme.Shark
import com.azzam.cryptocurrency.ui.theme.WhiteWith40PercentOpacity
import com.azzam.cryptocurrency.ui.theme.labelMedium
import com.azzam.cryptocurrency.ui.theme.titleMedium

@Composable
fun CryptoCurrencyListItem(
    model: CryptoCurrencyUiModel?,
    modifier: Modifier = Modifier,
    itemClickAction: (CryptoCurrencyUiModel?) -> Unit
) {

    val changePercentColor = getChangePercentColor(model?.changePercent)
    ConstraintLayout(
        modifier = modifier
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                shape = RoundedCornerShape(16.dp),
                color = WhiteWith40PercentOpacity
            )
            .clickable {
                itemClickAction.invoke(model)
            }
            .padding(
                start = 12.dp,
                top = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {

        val (image, clMainLabels, clPriceChange) = createRefs()
        CustomAsyncImage(
            image = model?.icon,
            size = 56.dp,
            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(clMainLabels) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(image.end, 16.dp)
                    end.linkTo(clPriceChange.start, 5.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            val (tvName, tvSymbol) = createRefs()

            Text(
                getValueOrNoData(model?.name),
                style = titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Shark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(tvName) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                getValueOrNoData(model?.symbol),
                style = labelMedium,
                color = Shark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(tvSymbol) {
                        top.linkTo(tvName.bottom, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(clPriceChange) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(clMainLabels.end)
                }
        ) {
            val (tvPrice, tvChange) = createRefs()

            Text(
                getValueOrNoData(model?.price),
                style = labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Shark,
                maxLines = 2,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(tvPrice) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                model?.displayChangePercent ?: "00.00%",
                style = labelMedium.copy(fontWeight = FontWeight.Bold),
                color = changePercentColor,
                maxLines = 2,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(tvChange) {
                        top.linkTo(tvPrice.bottom, 16.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

        }
    }
}

@Preview
@Composable
private fun CoinListItemPreview() {
    CryptoCurrencyTheme {
        CryptoCurrencyListItem(
            model = cryptoCurrencyUiModel1,
            itemClickAction = { /**/ },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}