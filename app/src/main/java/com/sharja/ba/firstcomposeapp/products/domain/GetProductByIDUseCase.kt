package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.presentation.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductByIDUseCase @Inject constructor(
    private val repository: Repository,
    private val mapperClass: MapperClass
) {
    operator fun invoke(productId: Int): Flow<State> = flow {
        emit(State.OnLoading)
        try {
            repository.getProductById(productId).map { localProduct ->
                State.OnSuccess(listOf(mapperClass.mapLocalProductToProduct(localProduct)))
            }.catch {
                emit(State.OnFailed(it.message.toString()))
            }.collect {
                emit(it)
            }
        } catch (ex: Exception) {
            emit(State.OnFailed(ex.message.toString()))
        }
    }
}
