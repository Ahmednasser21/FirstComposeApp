package com.sharja.ba.firstcomposeapp.presentation.productdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.data.Repository
import com.sharja.ba.firstcomposeapp.presentation.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf<ProductsState>(ProductsState.OnLoading)
        private set

    private fun getProductById() {
        viewModelScope.launch(Dispatchers.IO) {
//          val id = savedStateHandle.get<Int>("product_id")?: 0
            val id = savedStateHandle["productId"] ?: 0
            if (id == 0) {
                state =
                    ProductsState.OnFailed(IllegalArgumentException("Product ID is missing or invalid."))
            }

            repository.getRemoteProductByID(id)
                .catch { throwable ->
                    state = ProductsState.OnFailed(throwable)
                }
                .collect { product ->
                    state = ProductsState.OnSuccess(product)
                }
        }

    }

    init {
        getProductById()
    }

}
