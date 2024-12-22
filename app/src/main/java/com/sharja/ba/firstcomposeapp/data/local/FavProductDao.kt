package com.sharja.ba.firstcomposeapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavProductsList(favProductsList: List<FavProduct>)

    @Delete
    suspend fun deleteFavProduct(favProduct: FavProduct)

    @Update
    suspend fun updateFavProduct(favProduct: FavProduct)

    @Query("SELECT * FROM FavProducts")
   fun getAllFavProducts(): Flow<List<FavProduct>>

    @Query("SELECT * FROM FavProducts WHERE id = :id")
    fun getFavProductById(id: Int): Flow<FavProduct>
}