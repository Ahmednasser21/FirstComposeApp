package com.sharja.ba.firstcomposeapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.sharja.ba.firstcomposeapp.ui.theme.FirstComposeAppTheme
import com.sharja.ba.firstcomposeapp.presentation.home.HomeScreen
import com.sharja.ba.firstcomposeapp.presentation.productdetails.ProductDetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeAppTheme {
                DummyProductsAppNewNav()
            }
        }
    }
}

@Composable
fun DummyProductsApp() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "products") {

        composable(route = "products") {
            HomeScreen { productId ->
//                val productJson = Uri.encode( Json.encodeToString(product))
                navController.navigate("productDetails/$productId")
            }
        }

        composable(
            route = "productDetails/{product_id}",
            arguments = listOf(navArgument("product_id") { type = NavType.IntType })
        ) { backStackEntry ->
//            val productJson = backStackEntry.arguments?.getString("productJson") ?: ""
//            val product = Json.decodeFromString<Product>(productJson)
            ProductDetailsScreen ()
        }
    }
}
@Composable
fun DummyProductsAppNewNav() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {

        composable<Home> {
            HomeScreen { productId ->

//                val productJson = Json.encodeToString(product)
                navController.navigate(ProductDetails(productId))
            }
        }

        composable<ProductDetails>{ backStackEntry ->
//            val productDetails:ProductDetails = backStackEntry.toRoute()
//            val product = Json.decodeFromString<Product>(productDetails.product)
            ProductDetailsScreen ()
        }
    }
}

@Serializable
object Home

@Serializable
data class ProductDetails(val productId:Int)

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    FirstComposeAppTheme {
//        HomeScreen()
//    }
//}
