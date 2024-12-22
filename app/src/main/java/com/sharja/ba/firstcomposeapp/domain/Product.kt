package com.sharja.ba.firstcomposeapp.domain

import kotlinx.serialization.Serializable


@Serializable
data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String= "",
    val price: String= "",
    val rating: String = "",
    val brand: String= "",
    val images: List<String> = listOf("")
)
