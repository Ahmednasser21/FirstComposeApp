package com.sharja.ba.firstcomposeapp.products.presentation


sealed class State {
    class OnSuccess<T>(val data:T):State()
    class OnFailed(val error:String):State()
    data object OnLoading:State()
}