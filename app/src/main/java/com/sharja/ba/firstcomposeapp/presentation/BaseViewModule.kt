package com.sharja.ba.firstcomposeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.data.Repository
import com.sharja.ba.firstcomposeapp.data.local.FavProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModule (
   protected val repository: Repository
):ViewModel() {

    fun toggleFavourite(favProduct: FavProduct){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateFavProduct(favProduct)
        }
    }
}