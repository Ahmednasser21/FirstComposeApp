package com.sharja.ba.firstcomposeapp.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharja.ba.firstcomposeapp.data.Repository
import com.sharja.ba.firstcomposeapp.data.local.FavProduct
import com.sharja.ba.firstcomposeapp.domain.Product
import com.sharja.ba.firstcomposeapp.presentation.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var favState by mutableStateOf<ProductsState>(ProductsState.OnLoading)
        private set

    init {
        getProductList()
    }

    private fun getProductList() {
        viewModelScope.launch(Dispatchers.IO) {

            repository.getRemoteProductsList()
                .catch { error ->
                    Log.e(TAG, "getProductList: ",error )
                }
                .collect { productResponse ->
                    repository.insertFavProduct(productResponse.products.map {product ->
                        FavProduct(
                            id = product.id,
                            title = product.title,
                            description = product.description,
                            price = product.price,
                            rating = product.rating,
                            brand = product.brand,
                            images = product.images[0])
                    })
                }
            getAllFav()
        }
    }
    private fun getAllFav(){
        viewModelScope.launch (Dispatchers.IO){
            repository.getAllFav()
                .catch {
                    favState = ProductsState.OnFailed(it)
                }
                .collect{favProducts->

                    favState = ProductsState.OnSuccess(favProducts)
                }
        }
    }
    fun toggleFavourite(favProduct: FavProduct){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateFavProduct(favProduct)
            getAllFav()
        }
    }
}