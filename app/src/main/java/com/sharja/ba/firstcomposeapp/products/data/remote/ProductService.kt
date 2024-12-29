package com.sharja.ba.firstcomposeapp.products.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
@GET("products")
suspend fun getRemoteProducts() : ProductResponse

@GET("products/{id}")
suspend fun getProductById(@Path("id") id:Int): RemoteProduct
}