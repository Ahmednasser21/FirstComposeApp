package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.presentation.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalProductsUseCase @Inject constructor(
    private val repository: Repository,
    private val mapperClass: MapperClass
) {
    operator fun invoke(): Flow<State> = flow {
        repository.getAllLocalProducts().catch { throwable->
            emit(State.OnFailed(throwable.message.toString()))
        }.collect{productsList->
            val products = mapperClass.mapLocalProductListToProductList(productsList)
            emit(State.OnSuccess(products))
        }
    }
}