package com.sharja.ba.firstcomposeapp.products.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "FavProducts")
data class LocalProduct(
    @PrimaryKey val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val rating: String = "",
    val brand: String = "",
    val images: String = "",
    val isFav: Boolean = false
)
