package com.sharja.ba.firstcomposeapp.products.presentation.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.domain.MapperClass
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import com.sharja.ba.firstcomposeapp.products.presentation.BaseViewModule
import com.sharja.ba.firstcomposeapp.products.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle,
    private val mapperClass: MapperClass,
    updateFavouriteUseCase: UpdateFavouriteUseCase
) : BaseViewModule(updateFavouriteUseCase) {
   private var _productDetailsState = MutableStateFlow<State>(State.OnLoading)
    val productDetailsState = _productDetailsState.asStateFlow()
        .onStart {
            getProductById()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            State.OnLoading
        )

    private fun getProductById() {
        viewModelScope.launch {
//          val id = savedStateHandle.get<Int>("product_id")?: 0
            val id = savedStateHandle["productId"] ?: 0
            if (id == 0) {
                _productDetailsState.value =
                    State.OnFailed("Product ID is missing or invalid.")
                return@launch
            }

            repository.getProductById(id)
                .catch { throwable ->
                    _productDetailsState.value = State.OnFailed(throwable.message.toString())
                }
                .collect { localProduct ->
                    val product = mapperClass.mapLocalProductToProduct(localProduct)
                    _productDetailsState.value = State.OnSuccess(listOf( product))
                }
        }

    }
}
