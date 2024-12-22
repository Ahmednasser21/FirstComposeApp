package com.sharja.ba.firstcomposeapp.data.di

import android.content.Context
import androidx.room.Room
import com.sharja.ba.firstcomposeapp.data.local.FavProductsDatabase
import com.sharja.ba.firstcomposeapp.data.local.FavProductDao
import com.sharja.ba.firstcomposeapp.data.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dummyjson.com/")
            .build()
    }

    @Singleton
    @Provides
    fun provideProductService(
        retrofit: Retrofit
    ): ProductService {
        return retrofit.create(ProductService::class.java)
    }
    @Singleton
    @Provides
    fun provideRoomDb(
       @ApplicationContext context: Context
    ):FavProductsDatabase{
        return Room.databaseBuilder(
            context,
            FavProductsDatabase::class.java,
            "fav_products_database"
        ).build()
    }
    @Singleton
    @Provides
    fun provideDao(
        favDatabase:FavProductsDatabase
    ): FavProductDao {
        return favDatabase.productDao()
    }
}