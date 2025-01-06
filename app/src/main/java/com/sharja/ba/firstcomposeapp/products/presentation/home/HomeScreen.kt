package com.sharja.ba.firstcomposeapp.products.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sharja.ba.firstcomposeapp.products.domain.Product
import com.sharja.ba.firstcomposeapp.products.presentation.ErrorSnackbar
import com.sharja.ba.firstcomposeapp.products.presentation.FavouriteIcon
import com.sharja.ba.firstcomposeapp.products.presentation.ProductImage
import com.sharja.ba.firstcomposeapp.products.presentation.Progressbar
import com.sharja.ba.firstcomposeapp.products.presentation.State
import com.sharja.ba.firstcomposeapp.theme.Typography


@Composable
fun HomeScreen(
    state: State,
    onItemClick: (Int) -> Unit,
    onFavClick: (id:Int,isFav:Boolean) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        when (state) {
            is State.OnLoading -> {
                Progressbar(paddingValues)
            }

            is State.OnSuccess -> {
                ProductList(
                    products = state.productList,
                    onItemClick = { onItemClick(it) },
                    onFavClick = { id,isFav ->
                       onFavClick(id,isFav)
                    })
            }

            is State.OnFailed -> {
                val error = state.error
                ErrorSnackbar(snackbarHostState, error)
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onItemClick: (productId: Int) -> Unit,
    onFavClick: (id:Int,isFav:Boolean) -> Unit
) {
    LazyColumn {
        items(products) { favProduct ->
            ProductItem(
                product = favProduct,
                onItemClick = { onItemClick(it) },
                onFavClick = { id,isFav->onFavClick(id,isFav)})
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onItemClick: (productId: Int) -> Unit,
    onFavClick: (id:Int,isFav:Boolean) -> Unit
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
                onItemClick(product.id)
            }
    ) {
        ProductImage(
            product.images,
            modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = product.title,
                fontSize = Typography.titleMedium.fontSize,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .weight(4f)
                    .padding(32.dp, 16.dp, 16.dp, 16.dp)

            )
            FavouriteIcon(
                product,
                modifier
                    .padding(0.dp, 16.dp, 16.dp, 16.dp)
                    .weight(1f)
            ){id,isFav->onFavClick(id,isFav)}

        }

    }
}
