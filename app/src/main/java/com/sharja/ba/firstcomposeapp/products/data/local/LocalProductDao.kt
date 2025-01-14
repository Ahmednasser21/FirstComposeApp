package com.sharja.ba.firstcomposeapp.products.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocalProductsList(localProductsList: List<LocalProduct>)

    @Query("SELECT * FROM FavProducts WHERE id = :productId")
    fun getProductByID(productId: Int): Flow<LocalProduct>

    @Query("UPDATE FavProducts SET isFav = :isFav WHERE id = :productId")
    suspend fun updateFavoriteStatus(productId: Int, isFav: Boolean)

    @Query("SELECT * FROM FavProducts")
    fun getAllLocalProducts(): Flow<List<LocalProduct>>
}
