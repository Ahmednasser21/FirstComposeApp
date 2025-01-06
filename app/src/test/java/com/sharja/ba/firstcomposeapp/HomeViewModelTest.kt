package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.domain.GetLocalProductsUseCase
import com.sharja.ba.firstcomposeapp.products.domain.MapperClass
import com.sharja.ba.firstcomposeapp.products.domain.SyncAndGetLocalProductUseCase
import com.sharja.ba.firstcomposeapp.products.domain.SyncRemoteProductsUseCase
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import com.sharja.ba.firstcomposeapp.products.presentation.State
import com.sharja.ba.firstcomposeapp.products.presentation.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)


    @Test
    fun loadingState_IsSetCorrectly() = scope.runTest {

        val homeViewModel = getHomeViewModel()
        val state = homeViewModel.productsState.value
        assert(state == State.OnLoading)
    }

    @Test
    fun loadedContentState_IsSetCorrectly() = scope.runTest {
        val homeViewModel = getHomeViewModel()
        advanceUntilIdle()
        val state = homeViewModel.productsState.value
        assert(state == State.OnSuccess(DummyProductLists.getDummyProductsList()))
    }


    private fun getHomeViewModel(): HomeViewModel {

        val repository = Repository(
            productService = TestProductService(),
            localProductDao = TestProductDao()
        )
        val updateFavouriteUseCase = UpdateFavouriteUseCase(repository)
        val mapperClass = MapperClass()
        val syncRemoteProductsUseCase = SyncRemoteProductsUseCase(repository, mapperClass)
        val getLocalProductUseCase = GetLocalProductsUseCase(repository)
        val syncAndGetLocalProductUseCase = SyncAndGetLocalProductUseCase(
            syncRemoteProductsUseCase = syncRemoteProductsUseCase,
            getLocalProductsUseCase = getLocalProductUseCase,
            mapperClass = mapperClass
        )

        return HomeViewModel(
            updateFavouriteUseCase = updateFavouriteUseCase,
            syncAndGetLocalProductUseCase = syncAndGetLocalProductUseCase,
            dispatcher = dispatcher
        )
    }

}