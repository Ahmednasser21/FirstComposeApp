package com.sharja.ba.firstcomposeapp.products.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RemoteProduct(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val rating: String = "",
    val brand: String = "",
    val images: List<String> = listOf("")
)
