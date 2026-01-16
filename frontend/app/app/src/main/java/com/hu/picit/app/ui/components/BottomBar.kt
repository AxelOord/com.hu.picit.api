package com.hu.picit.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hu.picit.app.model.SharedCartViewModel

private data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedItem: ImageVector,
    val unselectedItem: ImageVector,
    val badgeCount: Int? = null
)

@Composable
fun BottomBar(
    navController: NavController,
    sharedCartViewModel: SharedCartViewModel,
    testTagPrefix: String = "bottomBar"
) {
    val cartItems by sharedCartViewModel.cartItems.collectAsState()

    val navItems = listOf(
        BottomNavigationItem("Home", Screen.Home.route, Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavigationItem("Categorieën", Screen.Categories.route, Icons.Filled.Search, Icons.Outlined.Search),
        BottomNavigationItem("Favorieten", Screen.Favorites.route, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
        BottomNavigationItem(
            "Winkelwagen",
            Screen.Cart.route,
            Icons.Filled.ShoppingCart,
            Icons.Outlined.ShoppingCart,
            badgeCount = cartItems.size
        )
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.testTag(testTagPrefix)
    ) {
        navItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                modifier = Modifier
                    .testTag("$testTagPrefix-item-${item.route}")
                    // makes selection assertable without depending on visuals
                    .semantics { stateDescription = if (isSelected) "selected" else "unselected" },
                icon = {
                    BadgedBox(
                        badge = {
                            item.badgeCount?.takeIf { it > 0 }?.let { count ->
                                Badge(modifier = Modifier.testTag("$testTagPrefix-badge-${item.route}")) {
                                    Text(text = count.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedItem else item.unselectedItem,
                            contentDescription = item.title,
                            tint = if (isSelected && item.route == Screen.Favorites.route) Color.Red else Color.Black
                        )
                    }
                },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            if (item.route == Screen.Home.route || currentRoute == Screen.Cart.route) {
                                popUpTo(item.route)
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}
