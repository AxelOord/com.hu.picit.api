package com.hu.picit.app.ui.screens.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hu.picit.app.model.sampleFruits


@Composable
fun CartScreen(
    cartUiState: CartUiState,
    modifier: Modifier = Modifier,
    navController: NavController?
) {
    when (cartUiState) {
        is CartUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CartUiState.Success -> {
            ResultScreen(navController = navController)
        }
        is CartUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}