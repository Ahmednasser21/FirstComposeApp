package com.sharja.ba.firstcomposeapp.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sharja.ba.firstcomposeapp.data.local.FavProduct
import com.sharja.ba.firstcomposeapp.presentation.ProductsState
import com.sharja.ba.firstcomposeapp.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun HomeScreen(
    onItemClick: (Int) -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        when (val productState = homeViewModel.favState) {
            is ProductsState.OnLoading -> {
                ShowingProgressbar(paddingValues)
            }

            is ProductsState.OnSuccess<*> -> {
                ProductPreviewList(
                    products = (productState as ProductsState.OnSuccess<List<FavProduct>>).data,
                    onItemClick= { onItemClick(it) },
                    onFavClick = {favProduct ->
                        homeViewModel.toggleFavourite(favProduct)
                    })
            }

            is ProductsState.OnFailed -> {
                val error = productState.error.message

                ShowingErrorSnackbar(snackbarHostState, error)
            }
        }
    }
}

@Composable
fun ShowingProgressbar(paddingValues: PaddingValues) {
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
fun ShowingErrorSnackbar(snackbarHostState: SnackbarHostState, error: String?) {

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = error.toString(),
            duration = SnackbarDuration.Long
        )
    }
}

@Composable
fun ProductPreviewList(
    products: List<FavProduct>,
    onItemClick: (productId: Int) -> Unit,
    onFavClick:(FavProduct)->Unit) {
    LazyColumn {
        items(products) { favProduct ->
            ProductPreviewItem(
                favProduct = favProduct,
                onItemClick = {onItemClick(it) },
                onFavClick = {onFavClick(it)})
        }
    }
}

@Composable
fun ProductPreviewItem(
    favProduct: FavProduct,
    modifier: Modifier = Modifier,
    onItemClick: (productId: Int) -> Unit,
    onFavClick: (FavProduct) -> Unit
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .clickable {
                onItemClick(favProduct.id)
            }
    ) {
        GlideImage(
            favProduct.images,
            modifier = modifier
                .height(250.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = favProduct.title,
                fontSize = Typography.titleMedium.fontSize,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .weight(4f)
                    .padding(16.dp)

            )
            Icon(
                imageVector = if (favProduct.isFav)Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Add To Fav icon",
                tint = if (favProduct.isFav) Color.Red else Color.Black,
                modifier = modifier
                    .padding(16.dp)
                    .weight(1f)
                    .clickable {
                        onFavClick(favProduct.copy(isFav = !favProduct.isFav))
                    }
            )
        }

    }
}