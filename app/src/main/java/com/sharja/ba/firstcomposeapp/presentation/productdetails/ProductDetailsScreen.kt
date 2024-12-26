package com.sharja.ba.firstcomposeapp.presentation.productdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sharja.ba.firstcomposeapp.data.local.FavProduct
import com.sharja.ba.firstcomposeapp.presentation.ProductsState
import com.sharja.ba.firstcomposeapp.presentation.home.ShowFavouriteIcon
import com.sharja.ba.firstcomposeapp.presentation.home.ShowProductImage
import com.sharja.ba.firstcomposeapp.presentation.home.ShowingErrorSnackbar
import com.sharja.ba.firstcomposeapp.presentation.home.ShowingProgressbar
import com.sharja.ba.firstcomposeapp.ui.theme.Typography

@Composable
fun ProductDetailsScreen() {
    val productDetailsViewModle: ProductDetailsViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        when (val state = productDetailsViewModle.state) {
            is ProductsState.OnLoading -> {
                ShowingProgressbar(paddingValues)
            }

            is ProductsState.OnSuccess<*> -> {
                val product = (state as ProductsState.OnSuccess<FavProduct>).data
                ProductItem(product, onFavClick = {
                    productDetailsViewModle.toggleFavourite(it)
                })
            }

            is ProductsState.OnFailed -> {
                val error = state.error.message
                ShowingErrorSnackbar(snackbarHostState, error)
            }
        }
    }

}

@Composable
fun ProductItem(favProduct: FavProduct, modifier: Modifier = Modifier,onFavClick:(FavProduct)->Unit) {
    val rating = Math.round(favProduct.rating.toFloat())

    Column(
        modifier = modifier.fillMaxSize()
            .padding(vertical = 16.dp),
        Arrangement.SpaceBetween
    ) {
        ShowProductImage(
            favProduct.images,
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.6f),
            )
        Text(
            text = favProduct.title,
            modifier = modifier.align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = Typography.headlineSmall.fontSize,
            color = MaterialTheme.colorScheme.primary
        )
        Row(modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(
                text = "Brand: ${favProduct.brand}",
                textAlign = TextAlign.Start,
                fontSize = Typography.titleMedium.fontSize,
                modifier = modifier
                    .weight(3f)

            )
            Text(
                text = "Price: ${favProduct.price}$",
                textAlign = TextAlign.End,
                fontSize = Typography.titleMedium.fontSize,
                modifier = modifier
                    .weight(2f)
            )
        }

        Text(
            text = "Description: ${favProduct.description}",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
        )

        Row(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Rating:",
                textAlign = TextAlign.Start,
                modifier = modifier
            )
            RatingBar(rating, modifier = modifier)
            Spacer(modifier = Modifier.weight(1f))
            ShowFavouriteIcon(
                favProduct,
                modifier,
                onFavClick
            )
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
                    .size(24.dp)
            )
        }
    }
    Text(
        text = "($rating)",
        fontSize = 14.sp,

    )
}