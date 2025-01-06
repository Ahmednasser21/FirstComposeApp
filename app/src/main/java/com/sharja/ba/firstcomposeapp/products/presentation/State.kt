package com.sharja.ba.firstcomposeapp.products.presentation

import com.sharja.ba.firstcomposeapp.products.domain.Product

sealed class State {
    data class OnSuccess(val productList:List<Product>):State()
    data class OnFailed(val error:String):State()
    data object OnLoading:State()
}