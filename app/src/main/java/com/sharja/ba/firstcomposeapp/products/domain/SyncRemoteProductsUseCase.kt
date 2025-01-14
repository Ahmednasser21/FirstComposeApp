package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRemoteProductsUseCase @Inject constructor(
    private val repository: Repository,
    private val mapperClass: MapperClass
) {
    operator fun invoke(): Flow<DomainState> = flow {
        try {
            repository.getRemoteProductsList().map { productResponse ->
                DomainState.OnSuccess(
                    mapperClass.mapRemoteProductListToLocalProductList(
                        productResponse.products
                    )
                )
            }.catch {
                emit(DomainState.OnFailed("No internet connection\nFailed to updated local data"))
            }.collect {
                repository.insertLocalProduct(it.localProductList)
                emit(it)
            }
        } catch (ex: Exception) {
            emit(DomainState.OnFailed(ex.message.toString()))
        }
    }
}
