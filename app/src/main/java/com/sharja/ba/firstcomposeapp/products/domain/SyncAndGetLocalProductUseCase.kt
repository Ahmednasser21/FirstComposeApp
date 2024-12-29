package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.presentation.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncAndGetLocalProductUseCase @Inject constructor(
    private val syncRemoteProductsUseCase: SyncRemoteProductsUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase
) {
    private suspend fun FlowCollector<State>.emitLocalState() {
        getLocalProductsUseCase().collect { localState ->
            emit(localState)
        }
    }
    operator fun invoke(): Flow<State> = flow {
        syncRemoteProductsUseCase().collect { remoteState ->
            when (remoteState) {
                is State.OnSuccess<*> -> {
                    emitLocalState()
                }
                is State.OnFailed ->{
                    emit(remoteState)
                    delay(2000)
                    emitLocalState()
                }
                is State.OnLoading->{
                    emit(remoteState)
                }
            }
        }
    }
}
