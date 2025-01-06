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
    private val getLocalProductsUseCase: GetLocalProductsUseCase,
    private val mapperClass: MapperClass
) {
    private suspend fun FlowCollector<State>.emitLocalState() {
        getLocalProductsUseCase().collect { localState ->
            if (localState is DomainState.OnSuccess){
                val productList =mapperClass.mapLocalProductListToProductList(localState.localProductList)
                emit(State.OnSuccess(productList))
            }else if (localState is DomainState.OnFailed){
                emit(State.OnFailed(localState.error))
            }
        }
    }
    operator fun invoke(): Flow<State> = flow {
        emit(State.OnLoading)
        try {
            syncRemoteProductsUseCase().collect { remoteState ->
                when (remoteState) {
                    is DomainState.OnSuccess -> {
                        emitLocalState()
                    }
                    is DomainState.OnFailed ->{
                        emit(State.OnFailed(remoteState.error))
                        delay(2000)
                        emitLocalState()
                    }
                }
            }
        }catch (ex:Exception){
            emit(State.OnFailed(ex.message.toString()))
        }

    }
}
