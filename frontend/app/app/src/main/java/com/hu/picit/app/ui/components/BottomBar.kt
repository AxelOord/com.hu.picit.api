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
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

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
) {
    val navItems = listOf(
        BottomNavigationItem(
            title = "Home",
            route = Screen.Home.route,
            selectedItem = Icons.Filled.Home,
            unselectedItem = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Categorieën",
            route = Screen.Categories.route,
            selectedItem = Icons.Filled.Search,
            unselectedItem = Icons.Outlined.Search
        ),
        BottomNavigationItem(
            title = "Favorieten",
            route = Screen.Favorites.route,
            selectedItem = Icons.Filled.Favorite,
            unselectedItem = Icons.Outlined.FavoriteBorder
        ),
        BottomNavigationItem(
            title = "Winkelwagen",
            route = Screen.Cart.route,
            selectedItem = Icons.Filled.ShoppingCart,
            unselectedItem = Icons.Outlined.ShoppingCart,
            badgeCount = 12
        )
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        navItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    BadgedBox(
                        badge = {
                            item.badgeCount?.let {
                                Badge {
                                    Text(text = it.toString())
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
                            if (item.route == Screen.Home.route) {
                                popUpTo(Screen.Home.route)
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
