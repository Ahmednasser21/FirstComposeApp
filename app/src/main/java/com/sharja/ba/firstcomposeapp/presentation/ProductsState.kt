package com.sharja.ba.firstcomposeapp.presentation


sealed class ProductsState {
    class OnSuccess<T>(val data:T):ProductsState()
    class OnFailed(val error:Throwable):ProductsState()
    object OnLoading:ProductsState()
}