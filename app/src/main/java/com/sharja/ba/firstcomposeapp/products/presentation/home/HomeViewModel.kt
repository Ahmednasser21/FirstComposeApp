package com.sharja.ba.firstcomposeapp.products.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.products.domain.SyncAndGetLocalProductUseCase
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import com.sharja.ba.firstcomposeapp.products.presentation.BaseViewModule
import com.sharja.ba.firstcomposeapp.products.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val syncAndGetLocalProductUseCase: SyncAndGetLocalProductUseCase
) : BaseViewModule(updateFavouriteUseCase) {
    private var _productsState = MutableStateFlow<State>(State.OnLoading)
    val productsState =_productsState.asStateFlow()
        .onStart { getProductList() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            State.OnLoading
        )

    private fun getProductList() {
        viewModelScope.launch {
            syncAndGetLocalProductUseCase().collect { result ->
                _productsState.value = result
            }
        }
    }
}