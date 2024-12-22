package com.sharja.ba.firstcomposeapp.data.remote

import com.sharja.ba.firstcomposeapp.domain.Product
import com.sharja.ba.firstcomposeapp.domain.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
@GET("products")
suspend fun getRemoteProducts() : ProductResponse

@GET("products/{id}")
suspend fun getProductById(@Path("id") id:Int):Product
}