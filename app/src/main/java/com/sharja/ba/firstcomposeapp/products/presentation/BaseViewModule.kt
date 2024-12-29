package com.sharja.ba.firstcomposeapp.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import kotlinx.coroutines.launch

abstract class BaseViewModule (
    private val updateFavouriteUseCase: UpdateFavouriteUseCase
):ViewModel() {

    fun toggleFavourite(productId:Int,isFav:Boolean){
        viewModelScope.launch {
           updateFavouriteUseCase(productId,isFav)
        }
    }
}