import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.hu.picit.app.ui.screens.home.HomeScreen
import com.hu.picit.app.ui.screens.home.HomeViewModel
import com.hu.picit.app.ui.screens.search.SearchScreen
import com.hu.picit.app.ui.screens.search.SearchViewModel
import kotlinx.serialization.Serializable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.animation.core.tween
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.hu.picit.app.model.SharedFruitViewModel
import com.hu.picit.app.ui.components.BottomBar
import com.hu.picit.app.ui.screens.cart.CartScreen
import com.hu.picit.app.ui.screens.cart.CartViewModel
import com.hu.picit.app.ui.screens.product.ProductScreen
import com.hu.picit.app.ui.screens.product.ProductViewModel

@Serializable
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Product : Screen("product")
    object Categories : Screen("categories")
    object Favorites : Screen("favorites")
    object Cart : Screen("cart")
}

@Composable
fun PicitApp() {
    val navController = rememberNavController()
    val sharedViewModel: SharedFruitViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = contentPadding.calculateBottomPadding())
        ) {
            NavHost(
                navController = navController,
                startDestination = "PicitRoute",
                ) {
                picitGraph(navController, sharedViewModel)
            }
        }
    }
}

fun NavGraphBuilder.picitGraph(navController: NavController, sharedViewModel: SharedFruitViewModel) {
    navigation(
        startDestination = Screen.Home.route, route = "PicitRoute"
    ) {
        // Home Screen with unique animations
        composable(
            Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = {ExitTransition.None},
            popEnterTransition = { defaultPopEnterTransition() },
            popExitTransition = { defaultPopExitTransition() }
        ) {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                homeUiState = homeViewModel.homeUiState,
                navController = navController,
                sharedFruitViewModel = sharedViewModel
            )
        }

        composable(
            Screen.Product.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = {
                conditionalExitTransition(
                    targetRoute = targetState.destination.route ?: ""
                )
            },
            popEnterTransition = { defaultPopEnterTransition() },
            popExitTransition = { defaultPopExitTransition() }
        ) {
            val productViewModel: ProductViewModel = viewModel()
            ProductScreen(
                productUiState = productViewModel.productUiState,
                navController = navController,
                sharedFruitViewModel = sharedViewModel
            )
        }

        composable(
            Screen.Categories.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = {
                conditionalExitTransition(
                    targetRoute = targetState.destination.route ?: ""
                )
            },
            popEnterTransition = { defaultPopEnterTransition() },
            popExitTransition = { defaultPopExitTransition() }
        ) {
            val searchViewModel: SearchViewModel = viewModel()
            SearchScreen(
                searchUiState = searchViewModel.searchUiState,
                navController = navController,
                sharedFruitViewModel = sharedViewModel
            )
        }

        composable(
            Screen.Favorites.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = {
                conditionalExitTransition(
                    targetRoute = targetState.destination.route ?: ""
                )
            },
            popEnterTransition = { defaultPopEnterTransition() },
            popExitTransition = { defaultPopExitTransition() }
        ) {
            val searchViewModel: SearchViewModel = viewModel()
            SearchScreen(
                searchUiState = searchViewModel.searchUiState,
                navController = navController,
                sharedFruitViewModel = sharedViewModel,
                allFavorite = true
            )
        }

        composable(
            Screen.Cart.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = {
                conditionalExitTransition(
                    targetRoute = targetState.destination.route ?: ""
                )
            },
            popEnterTransition = { defaultPopEnterTransition() },
            popExitTransition = { defaultPopExitTransition() }
        ) {
            val cartViewModel: CartViewModel = viewModel()
            CartScreen(
                navController = navController,
                cartUiState = cartViewModel.cartUiState
            )
        }
    }
}

private fun defaultEnterTransition() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(700)
)

private fun conditionalExitTransition(targetRoute: String) = if(targetRoute == Screen.Home.route) {
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(700)
    )
} else {
    defaultExitTransition()
}

private fun defaultExitTransition() = ExitTransition.None

private fun defaultPopEnterTransition() = EnterTransition.None

private fun defaultPopExitTransition() = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(700)
)
