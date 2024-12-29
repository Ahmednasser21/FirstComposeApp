package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.local.LocalProduct
import com.sharja.ba.firstcomposeapp.products.data.remote.RemoteProduct
import javax.inject.Inject

class MapperClass @Inject constructor() {
    fun mapRemoteProductListToLocalProductList(remoteProducts: List<RemoteProduct>): List<LocalProduct> {
        return remoteProducts.map { product ->
            LocalProduct(
                id = product.id,
                title = product.title,
                description = product.description,
                price = product.price,
                rating = product.rating,
                brand = product.brand,
                images = product.images[0]
            )
        }
    }

    fun mapLocalProductListToProductList(localProducts: List<LocalProduct>): List<Product> {
        return localProducts.map { localProduct ->
            Product(
                localProduct.id,
                localProduct.title,
                localProduct.description,
                localProduct.price,
                localProduct.rating,
                localProduct.brand,
                localProduct.images,
                localProduct.isFav
            )
        }
    }

    fun mapLocalProductToProduct(localProduct: LocalProduct): Product =
        Product(
            localProduct.id,
            localProduct.title,
            localProduct.description,
            localProduct.price,
            localProduct.rating,
            localProduct.brand,
            localProduct.images,
            localProduct.isFav
        )
}