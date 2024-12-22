package com.sharja.ba.firstcomposeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FavProduct::class])
abstract class FavProductsDatabase : RoomDatabase() {
    abstract fun productDao():FavProductDao
}