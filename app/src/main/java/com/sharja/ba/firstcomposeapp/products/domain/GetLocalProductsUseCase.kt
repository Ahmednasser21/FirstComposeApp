package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalProductsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<DomainState> = flow {
        repository.getAllLocalProducts().catch { throwable ->
            emit(DomainState.OnFailed(throwable.message.toString()))
        }.collect { productList ->
            emit(DomainState.OnSuccess(productList))
        }
    }
}
