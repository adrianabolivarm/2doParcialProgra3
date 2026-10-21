package com.example.a2parciall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.a2parciall.ui.screens.FavoritesScreen
import com.example.a2parciall.ui.screens.MarketPostsScreen
import com.example.a2parciall.ui.screens.ProductDetailScreen
import com.example.a2parciall.ui.screens.ProductListScreen
import com.example.a2parciall.ui.theme._2ParciallTheme
import com.example.a2parciall.ui.viewmodel.FavoritesViewModel
import com.example.a2parciall.ui.viewmodel.MarketPostViewModel
import com.example.a2parciall.ui.viewmodel.ProductViewModel

enum class Screen {
    PRODUCT_LIST,
    PRODUCT_DETAIL,
    FAVORITES,
    MARKET_POSTS
}

class MainActivity : ComponentActivity() {

    private val productViewModel: ProductViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val marketPostViewModel: MarketPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2ParciallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(
                        productViewModel = productViewModel,
                        favoritesViewModel = favoritesViewModel,
                        marketPostViewModel = marketPostViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    productViewModel: ProductViewModel,
    favoritesViewModel: FavoritesViewModel,
    marketPostViewModel: MarketPostViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(Screen.PRODUCT_LIST) }
    val selectedProduct by productViewModel.selectedProduct.collectAsState()

    when (currentScreen) {
        Screen.PRODUCT_LIST -> {
            ProductListScreen(
                viewModel = productViewModel,
                onProductClick = { product ->
                    productViewModel.selectProduct(product)
                    currentScreen = Screen.PRODUCT_DETAIL
                },
                onNavigateToFavorites = { currentScreen = Screen.FAVORITES },
                onNavigateToMarketPosts = { currentScreen = Screen.MARKET_POSTS },
                modifier = modifier
            )
        }
        Screen.PRODUCT_DETAIL -> {
            selectedProduct?.let { product ->
                ProductDetailScreen(
                    product = product,
                    favoritesViewModel = favoritesViewModel,
                    onBackClick = { currentScreen = Screen.PRODUCT_LIST },
                    modifier = modifier
                )
            } ?: run {
                currentScreen = Screen.PRODUCT_LIST
            }
        }
        Screen.FAVORITES -> {
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onBackClick = { currentScreen = Screen.PRODUCT_LIST },
                modifier = modifier
            )
        }
        Screen.MARKET_POSTS -> {
            MarketPostsScreen(
                viewModel = marketPostViewModel,
                onBackClick = { currentScreen = Screen.PRODUCT_LIST },
                modifier = modifier
            )
        }
    }
}
