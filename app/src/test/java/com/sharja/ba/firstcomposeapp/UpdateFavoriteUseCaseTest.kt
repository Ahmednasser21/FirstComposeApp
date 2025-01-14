package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.data.Repository
import com.sharja.ba.firstcomposeapp.products.domain.UpdateFavouriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateFavoriteUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun updateFavState_IsSetCorrectly() = scope.runTest {
        val fakeProductDao = FakeProductDao()
        val updateFavouriteUseCase = UpdateFavouriteUseCase(
            Repository(FakeProductService(), fakeProductDao)
        )
        val productUnderTest = DummyProductLists.getDummyLocalProductsList()[0]
        val isFav = productUnderTest.isFav
        fakeProductDao.insertLocalProductsList(DummyProductLists.getDummyLocalProductsList())
        advanceUntilIdle()
        updateFavouriteUseCase(productUnderTest.id, isFav)
        advanceUntilIdle()
        fakeProductDao.getProductByID(productUnderTest.id).collect { updatedProduct ->
            assertEquals(updatedProduct.isFav, !isFav)
        }
    }
}
