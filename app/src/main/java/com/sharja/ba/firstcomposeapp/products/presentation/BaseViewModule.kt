package com.sharja.ba.firstcomposeapp.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.products.data.di.MainDispatcher
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

abstract class BaseViewModule(
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun toggleFavourite(productId: Int, isFav: Boolean) {
        viewModelScope.launch(dispatcher) {
            updateFavouriteUseCase(productId, isFav)
        }
    }
}
