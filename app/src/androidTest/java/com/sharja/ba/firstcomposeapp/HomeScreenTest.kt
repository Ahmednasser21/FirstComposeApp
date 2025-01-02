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
import com.sharja.ba.firstcomposeapp.products.presentation.home.HomeScreen
import com.sharja.ba.firstcomposeapp.theme.FirstComposeAppTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_IsActive() {
        testRule.setContent {
            FirstComposeAppTheme {
                HomeScreen(
                    state = State.OnLoading,
                    onFavClick = { _: Int, _: Boolean -> },
                    onItemClick = {}
                )

            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.HOME_lOADING)
            .assertIsDisplayed()
    }


    @Test
    fun errorState_IsActive(){
        val errorMsg = "Error"
        testRule.setContent {
            FirstComposeAppTheme {
                HomeScreen(
                    state = State.OnFailed(errorMsg),
                    onItemClick = {},
                    onFavClick = {_:Int,_:Boolean->}
                )
            }
        }
        testRule.onNodeWithText(errorMsg).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.HOME_lOADING).assertDoesNotExist()

    }

    @Test
    fun onSuccess_IsActive(){
        val dummyProductList = DummyProductsList.getDummyProductsList()
        testRule.setContent {
            FirstComposeAppTheme {
                HomeScreen(
                    state = State.OnSuccess<List<Product>>(dummyProductList),
                    onItemClick = {},
                    onFavClick = {_:Int,_:Boolean->}
                )
            }
        }
        testRule.onNodeWithText(dummyProductList[0].title).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.HOME_lOADING).assertDoesNotExist()
    }

    @Test
    fun onItemClicked_idIsPassedCorrectly(){
        val dummyProductList = DummyProductsList.getDummyProductsList()
        val productItem = dummyProductList[0]
        testRule.setContent {
            FirstComposeAppTheme {
                HomeScreen(
                    state = State.OnSuccess<List<Product>>(dummyProductList),
                    onFavClick = {_:Int,_:Boolean->},
                    onItemClick = {id:Int->
                        assert(id==productItem.id)
                    }
                )
            }
        }
        testRule.onNodeWithText(productItem.title).performClick()
    }

    @Test
    fun onFavClick_idAndIsFavArePassedCorrectly(){
        val dummyProductList = DummyProductsList.getDummyProductsList()
        val productItem = dummyProductList[0]
        testRule.setContent {
            FirstComposeAppTheme {
                HomeScreen(
                    state = State.OnSuccess<List<Product>>(dummyProductList),
                    onItemClick = {},
                    onFavClick = {id:Int,isFav:Boolean->
                        assert(id == productItem.id && isFav == productItem.isFav)
                    }
                )
            }
        }
        testRule.onNodeWithTag("fav_icon_${productItem.id}").performClick()
    }
}

