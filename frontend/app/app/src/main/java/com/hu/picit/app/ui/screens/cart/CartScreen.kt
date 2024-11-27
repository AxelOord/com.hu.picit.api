package com.hu.picit.app.ui.screens.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hu.picit.app.model.CartItem
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.model.sampleFruits

@Composable
fun CartScreen(
    cartUiState: CartUiState,
    modifier: Modifier = Modifier,
    sharedCartViewModel: SharedCartViewModel,
    navController: NavController
) {
    when (cartUiState) {
        is CartUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CartUiState.Success -> {
            ResultScreen(navController = navController, sharedCartViewModel = sharedCartViewModel)
        }
        is CartUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}