package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.domain.GetLocalProductsUseCase
import com.sharja.ba.firstcomposeapp.products.domain.MapperClass
import com.sharja.ba.firstcomposeapp.products.domain.SyncAndGetLocalProductUseCase
import com.sharja.ba.firstcomposeapp.products.domain.SyncRemoteProductsUseCase
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import com.sharja.ba.firstcomposeapp.products.presentation.State
import com.sharja.ba.firstcomposeapp.products.presentation.home.HomeViewModel
import junit.framework.TestCase.assertEquals
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

        val homeViewModel = getHomeViewModel(createRepo())
        val state = homeViewModel.productsState.value
        assertEquals(state, State.OnLoading)
    }

    @Test
    fun loadedContentState_IsSetCorrectly() = scope.runTest {

        val homeViewModel = getHomeViewModel(createRepo())
        advanceUntilIdle()
        val state = homeViewModel.productsState.value
        assertEquals(state, State.OnSuccess(DummyProductLists.getDummyProductsList()))
    }

    @Test
    fun toggleIsFav_IsSetCorrectly() = scope.runTest {

        val testProductDao = TestProductDao()
        val repository = Repository(
            productService = TestProductService(),
            localProductDao = testProductDao
        )
        testProductDao.insertLocalProductsList(DummyProductLists.getDummyLocalProductsList())
        
        val homeViewModel = getHomeViewModel(repository)

        val testProduct = DummyProductLists.getDummyLocalProductsList()[0]
        val isFav = testProduct.isFav

        homeViewModel.toggleFavourite(testProduct.id, isFav)
        advanceUntilIdle()

        testProductDao.getProductByID(testProduct.id).collect {
            assertEquals(it.isFav, !isFav)
        }

    }

    private fun createRepo(): Repository = Repository(
        productService = TestProductService(),
        localProductDao = TestProductDao()
    )

    private fun getHomeViewModel(repository: Repository): HomeViewModel {

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