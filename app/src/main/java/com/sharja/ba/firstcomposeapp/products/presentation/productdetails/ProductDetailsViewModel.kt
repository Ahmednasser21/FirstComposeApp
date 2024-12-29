package com.sharja.ba.firstcomposeapp.products.presentation.productdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.domain.MapperClass
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import com.sharja.ba.firstcomposeapp.products.presentation.BaseViewModule
import com.sharja.ba.firstcomposeapp.products.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle,
    private val mapperClass: MapperClass,
    updateFavouriteUseCase: UpdateFavouriteUseCase
) : BaseViewModule(updateFavouriteUseCase) {
    var state by mutableStateOf<State>(State.OnLoading)
        private set

    init {
        getProductById()
    }

    private fun getProductById() {
        viewModelScope.launch {
//          val id = savedStateHandle.get<Int>("product_id")?: 0
            val id = savedStateHandle["productId"] ?: 0
            if (id == 0) {
                state =
                    State.OnFailed("Product ID is missing or invalid.")
                return@launch
            }

            repository.getProductById(id)
                .catch { throwable ->
                    state = State.OnFailed(throwable.message.toString())
                }
                .collect { localProduct ->
                    val product = mapperClass.mapLocalProductToProduct(localProduct)
                    state = State.OnSuccess(product)
                }
        }

    }
}
