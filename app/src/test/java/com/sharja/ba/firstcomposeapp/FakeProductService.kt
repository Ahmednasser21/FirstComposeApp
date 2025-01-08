package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.data.remote.ProductResponse
import com.sharja.ba.firstcomposeapp.products.data.remote.ProductService
import com.sharja.ba.firstcomposeapp.products.data.remote.RemoteProduct

class FakeProductService: ProductService {
    override suspend fun getRemoteProducts(): ProductResponse {
        val productsList = DummyProductLists.getDummyRemoteProductList()
        return ProductResponse(productsList)
    }

    override suspend fun getProductById(id: Int): RemoteProduct {
        TODO("Not yet implemented")
    }

}