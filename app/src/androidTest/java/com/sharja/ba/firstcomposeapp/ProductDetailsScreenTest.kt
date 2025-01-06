package com.sharja.ba.firstcomposeapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sharja.ba.firstcomposeapp.products.domain.Product
import com.sharja.ba.firstcomposeapp.products.presentation.SemanticsDescription
import com.sharja.ba.firstcomposeapp.products.presentation.State
import com.sharja.ba.firstcomposeapp.products.presentation.productdetails.ProductDetailsScreen
import com.sharja.ba.firstcomposeapp.theme.FirstComposeAppTheme
import org.junit.Rule
import org.junit.Test

class ProductDetailsScreenTest {

    @get:Rule
    val testRrule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_IsActive() {
        testRrule.setContent {
            FirstComposeAppTheme {
                ProductDetailsScreen(
                    state = State.OnLoading,
                    onFavClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRrule.onNodeWithContentDescription(SemanticsDescription.HOME_lOADING)
            .assertIsDisplayed()
    }

    @Test
    fun onFailedState_IsActive() {
        val errorMsg = "Error"
        testRrule.setContent {
            FirstComposeAppTheme {
                ProductDetailsScreen(
                    state = State.OnFailed(errorMsg),
                    onFavClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRrule.onNodeWithText(errorMsg).assertIsDisplayed()
        testRrule.onNodeWithContentDescription(SemanticsDescription.HOME_lOADING)
            .assertDoesNotExist()
    }

    @Test
    fun onSuccessState_IsActive() {
        val product: Product = DummyProductsList.getDummyProductsList()[0]
        testRrule.setContent {
            FirstComposeAppTheme {
                ProductDetailsScreen(
                    state = State.OnSuccess(product),
                    onFavClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRrule.onNodeWithText(product.title).assertIsDisplayed()
        testRrule.onNodeWithContentDescription(SemanticsDescription.HOME_lOADING)
            .assertDoesNotExist()
    }

    @Test
    fun onFavClick_idAndIsFavArePassedCorrectly() {
        val product: Product = DummyProductsList.getDummyProductsList()[0]
        testRrule.setContent {
            FirstComposeAppTheme {
                ProductDetailsScreen(
                    state = State.OnSuccess(product),
                    onFavClick = { id: Int, isFav: Boolean ->
                        assert(id == product.id && isFav == product.isFav)
                    }
                )
            }
        }
        testRrule.onNodeWithTag("fav_icon_${product.id}").performClick()
    }
}