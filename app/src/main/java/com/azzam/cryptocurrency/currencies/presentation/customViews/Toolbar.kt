package com.azzam.cryptocurrency.currencies.presentation.customViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.currencies.presentation.utils.getValueOrNoData
import com.azzam.cryptocurrency.ui.theme.BlueWith30PercentOpacity
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme
import com.azzam.cryptocurrency.ui.theme.Shark
import com.azzam.cryptocurrency.ui.theme.titleLarge

@Composable
fun Toolbar(
    title: String?,
    showBackButton: Boolean = false,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .background(BlueWith30PercentOpacity)
            .statusBarsPadding()
            .height(55.dp)
            .padding(
                end = 24.dp,
            )
    ) {
        val (tvTitle, btnBack) = createRefs()

        if(showBackButton) {
            Image(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "Back Button",
                modifier = Modifier
                    .clickable {
                        onBackClick.invoke()
                    }
                    .size(48.dp)
                    .padding(16.dp)
                    .constrainAs(btnBack) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .testTag("btnBack")
            )
        }
        Text(
            getValueOrNoData(title),
            style = titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Shark,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(tvTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(
                        if (showBackButton) btnBack.end else parent.start,
                        if (showBackButton) 0.dp else 24.dp
                    )
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@Preview
@Composable
private fun ToolbarPreview() {
    CryptoCurrencyTheme {
        Toolbar(
            title = "New Title",
            onBackClick = { /**/ },
            showBackButton = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.background
                )
        )
    }
}