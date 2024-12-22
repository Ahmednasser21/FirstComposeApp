package com.sharja.ba.firstcomposeapp.data

import com.sharja.ba.firstcomposeapp.data.local.FavProductDao
import com.sharja.ba.firstcomposeapp.data.local.FavProduct
import com.sharja.ba.firstcomposeapp.data.remote.ProductService
import com.sharja.ba.firstcomposeapp.domain.Product
import com.sharja.ba.firstcomposeapp.domain.ProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val productService: ProductService,
    private val favDao: FavProductDao
) {
    fun getRemoteProductsList(): Flow<ProductResponse> {
        return flow {
           emit( productService.getRemoteProducts())
        }
    }
    fun getRemoteProductByID(id:Int): Flow<Product>{
        return flow {
            emit(productService.getProductById(id))
        }
    }
    suspend fun insertFavProduct(favProductsList: List<FavProduct>){
        favDao.insertFavProductsList(favProductsList)
    }
    suspend fun deleteFavProduct(favProduct: FavProduct){
        favDao.deleteFavProduct(favProduct)
    }
    suspend fun getAllFav():Flow<List<FavProduct>>{
        return favDao.getAllFavProducts()
    }
    suspend fun getFavProductById(id:Int):Flow<FavProduct>{
        return favDao.getFavProductById(id)
    }
    suspend fun updateFavProduct(favProduct: FavProduct){
        favDao.updateFavProduct(favProduct)
    }
}