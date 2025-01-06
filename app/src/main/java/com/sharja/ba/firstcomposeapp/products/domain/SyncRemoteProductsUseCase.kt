package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRemoteProductsUseCase @Inject constructor(
    private val repository: Repository,
    private val mapperClass: MapperClass
) {
    operator fun invoke(): Flow<DomainState> = flow {

        repository.getRemoteProductsList().catch {
            emit(DomainState.OnFailed("No internet connection\nFailed to updated local data"))
        }.collect{productResponse->
            val handledProductsList = mapperClass.mapRemoteProductListToLocalProductList(productResponse.products)
            repository.insertLocalProduct(handledProductsList)
            emit(DomainState.OnSuccess(handledProductsList))
        }
    }
}