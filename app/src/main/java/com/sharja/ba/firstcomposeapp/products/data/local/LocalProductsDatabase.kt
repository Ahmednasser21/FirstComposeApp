package com.sharja.ba.firstcomposeapp.products.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [LocalProduct::class], exportSchema = false)
abstract class LocalProductsDatabase : RoomDatabase() {
    abstract fun productDao(): LocalProductDao
}
