package com.sharja.ba.firstcomposeapp.products.data

import com.sharja.ba.firstcomposeapp.products.data.local.LocalProduct
import com.sharja.ba.firstcomposeapp.products.data.local.LocalProductDao
import com.sharja.ba.firstcomposeapp.products.data.remote.ProductResponse
import com.sharja.ba.firstcomposeapp.products.data.remote.ProductService
import com.sharja.ba.firstcomposeapp.products.data.remote.RemoteProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val productService: ProductService,
    private val localProductDao: LocalProductDao
) {
    fun getRemoteProductsList(): Flow<ProductResponse> = flow {
        emit(productService.getRemoteProducts())

    }

    fun getRemoteProductByID(id: Int): Flow<RemoteProduct> = flow {
        emit(productService.getProductById(id))
    }

    suspend fun insertLocalProduct(localProductsList: List<LocalProduct>) {
        localProductDao.insertLocalProductsList(localProductsList)
    }

    fun getAllLocalProducts(): Flow<List<LocalProduct>> = localProductDao.getAllLocalProducts()

    suspend fun updateFavState(productId: Int, isFav: Boolean) {
        localProductDao.updateFavoriteStatus(productId, isFav)
    }

    fun getProductById(productId: Int): Flow<LocalProduct> = localProductDao.getProductByID(productId)
}