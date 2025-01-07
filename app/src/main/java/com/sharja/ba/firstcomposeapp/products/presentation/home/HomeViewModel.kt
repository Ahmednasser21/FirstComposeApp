package com.sharja.ba.firstcomposeapp.products.presentation.home

import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.products.data.di.MainDispatcher
import com.sharja.ba.firstcomposeapp.products.domain.SyncAndGetLocalProductUseCase
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import com.sharja.ba.firstcomposeapp.products.presentation.BaseViewModule
import com.sharja.ba.firstcomposeapp.products.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val syncAndGetLocalProductUseCase: SyncAndGetLocalProductUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModule(updateFavouriteUseCase,dispatcher) {
    private val _productsState = MutableStateFlow<State>(State.OnLoading)
    val productsState =_productsState.asStateFlow()
    init {
        getProductList()
    }

    private fun getProductList() {
        viewModelScope.launch (dispatcher){
            syncAndGetLocalProductUseCase().collect { result ->
                _productsState.value = result
            }
        }
    }
}