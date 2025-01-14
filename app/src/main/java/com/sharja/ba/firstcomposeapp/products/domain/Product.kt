package com.sharja.ba.firstcomposeapp.products.domain

data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val rating: String = "",
    val brand: String = "",
    val images: String = "",
    val isFav: Boolean = false
)
