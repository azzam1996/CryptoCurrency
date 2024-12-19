package com.azzam.cryptocurrency.currencies.presentation.currenciesList.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.azzam.cryptocurrency.R
import com.azzam.cryptocurrency.ui.theme.CryptoCurrencyTheme

@Composable
fun CustomAsyncImage(
    modifier: Modifier = Modifier,
    image: Any? = null,
    contentDescription: String? = "",
    size: Dp = 56.dp,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_placeholder),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(size.div(3))
                )
            }
        },
        modifier = modifier
            .clip(CircleShape)
            .size(size)
    )
}

@Preview
@Composable
private fun AsyncImagePreview() {
    CryptoCurrencyTheme {
        CustomAsyncImage(
            image = R.drawable.ic_btc
        )
    }
}