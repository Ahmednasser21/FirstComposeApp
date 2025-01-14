package com.sharja.ba.firstcomposeapp.products.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sharja.ba.firstcomposeapp.products.domain.Product

@Composable
fun Progressbar(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                this.contentDescription = SemanticsDescription.HOME_lOADING
            }
        )
    }
}

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    error: String?
) {
    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = error.toString(),
            duration = SnackbarDuration.Long
        )
    }
}

@Composable
fun FavouriteIcon(
    localProduct: Product,
    modifier: Modifier,
    onFavClick: (id: Int, isFav: Boolean) -> Unit
) {
    Icon(
        imageVector = if (localProduct.isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = "Add To Fav icon of ${localProduct.title}",
        tint = if (localProduct.isFav) Color.Red else Color.Black,
        modifier = modifier
            .clickable {
                onFavClick(
                    localProduct.id,
                    localProduct.isFav
                )
            }
            .testTag("fav_icon_${localProduct.id}")
    )
}

@Composable
fun ProductImage(
    imageURL: String,
    modifier: Modifier
) {
    val painter = rememberAsyncImagePainter(imageURL)
    val state by painter.state.collectAsState()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                color = Color.Red
            )
        } else if (state is AsyncImagePainter.State.Success) {
            Image(
                painter = painter,
                modifier = modifier,
                contentDescription = "Product image",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
        }
    }
}
