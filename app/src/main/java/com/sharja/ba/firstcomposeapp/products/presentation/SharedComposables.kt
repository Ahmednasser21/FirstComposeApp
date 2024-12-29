package com.sharja.ba.firstcomposeapp.products.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.sharja.ba.firstcomposeapp.products.domain.Product
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Progressbar(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorSnackbar(snackbarHostState: SnackbarHostState, error: String?) {

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = error.toString(),
            duration = SnackbarDuration.Long
        )
    }
}

@Composable
fun ProductImage(imageURL: String, modifier: Modifier) {
    GlideImage(
        imageURL,
        modifier = modifier,
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )
}

@Composable
fun FavouriteIcon(
    localProduct: Product,
    modifier: Modifier,
    onFavClick: (Product) -> Unit
) {
    Icon(
        imageVector = if (localProduct.isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = "Add To Fav icon",
        tint = if (localProduct.isFav) Color.Red else Color.Black,
        modifier = modifier
            .clickable {
                onFavClick(localProduct)
            }
    )
}