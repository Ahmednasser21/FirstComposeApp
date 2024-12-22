package com.sharja.ba.firstcomposeapp.presentation.productdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sharja.ba.firstcomposeapp.domain.Product
import com.sharja.ba.firstcomposeapp.presentation.ProductsState
import com.sharja.ba.firstcomposeapp.presentation.home.ShowingErrorSnackbar
import com.sharja.ba.firstcomposeapp.presentation.home.ShowingProgressbar
import com.sharja.ba.firstcomposeapp.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProductDetailsScreen() {
    val vm: ProductDetailsViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        when (val state = vm.state) {
            is ProductsState.OnLoading -> {
                ShowingProgressbar(paddingValues)
            }

            is ProductsState.OnSuccess<*> -> {
                val product = (state as ProductsState.OnSuccess<Product>).data
                ProductItem(product)
            }

            is ProductsState.OnFailed -> {
                val error = state.error.message
                ShowingErrorSnackbar(snackbarHostState, error)
            }
        }
    }

}

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    val rating = Math.round(product.rating.toFloat())

    Column(
        modifier = modifier.fillMaxSize(),
        Arrangement.Top
    ) {
        GlideImage(
            product.images[0],
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.6f),
            contentScale = ContentScale.Fit
        )
        Text(
            text = product.title,
            modifier = modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
            fontSize = Typography.headlineSmall.fontSize,
            color = MaterialTheme.colorScheme.primary
        )
        Row(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Brand: ${product.brand}",
                textAlign = TextAlign.Start,
                fontSize = Typography.titleMedium.fontSize,
                modifier = modifier
                    .weight(3f)
                    .padding(16.dp)
            )
            Text(
                text = "Price: ${product.price}$",
                textAlign = TextAlign.End,
                fontSize = Typography.titleMedium.fontSize,
                modifier = modifier
                    .weight(2f)
                    .padding(16.dp)
            )
        }

        Text(
            text = "Description: ${product.description}",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
            textAlign = TextAlign.Start,
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rating:",
                textAlign = TextAlign.End,
                modifier = modifier.padding(start = 16.dp)
            )
            RatingBar(rating, modifier = modifier)
        }

    }
}

@Composable
fun RatingBar(
    rating: Int,
    maxRating: Int = 5,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= rating) Color.Yellow else Color.Black,
                modifier = modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
    Text(
        text = "($rating)",
        textAlign = TextAlign.End,
        fontSize = 14.sp,
    )
}