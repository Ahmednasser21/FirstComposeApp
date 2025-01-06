package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.data.local.LocalProduct
import com.sharja.ba.firstcomposeapp.products.data.local.LocalProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestProductDao : LocalProductDao {
    private val localProductsMap = HashMap<Int, LocalProduct>()
    override suspend fun insertLocalProductsList(localProductsList: List<LocalProduct>) {
        localProductsList.forEach {
            localProductsMap[it.id] = it
        }
    }

    override fun getProductByID(productId: Int): Flow<LocalProduct> {
        return flow {
            emit(localProductsMap[productId] ?: LocalProduct())
        }
    }

    override suspend fun updateFavoriteStatus(productId: Int, isFav: Boolean) {
        val product = localProductsMap[productId]
        val updatedProduct = product?.copy(isFav = !product.isFav)
        localProductsMap.replace(productId, updatedProduct ?: LocalProduct())
    }

    override fun getAllLocalProducts(): Flow<List<LocalProduct>> = flow {
        emit(localProductsMap.values.toList())
    }


}