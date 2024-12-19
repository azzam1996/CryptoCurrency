package com.azzam.cryptocurrency.currencies.presentation.currencyDetails.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.azzam.cryptocurrency.currencies.presentation.utils.getValueOrNoData
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme
import com.azzam.cryptocurrency.ui.theme.Shark
import com.azzam.cryptocurrency.ui.theme.labelMedium

@Composable
fun LabelValueItem(
    label: String,
    value: String?,
    valueColor: Color = Shark,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (tvLabel, tvValue) = createRefs()

        Text(
            label,
            style = labelMedium,
            color = Shark,
            maxLines = 2,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(tvLabel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(tvValue.start, 3.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            getValueOrNoData(value),
            style = labelMedium.copy(fontWeight = FontWeight.Bold),
            color = valueColor,
            maxLines = 2,
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(tvValue) {
                    top.linkTo(parent.top)
                    start.linkTo(tvLabel.end, 3.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@Preview
@Composable
private fun CoinListItemPreview() {
    CryptoCurrencyTheme {
        LabelValueItem(
            label = "Name Name Name Name Name Name Name",
            value = "John John John John John John",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}