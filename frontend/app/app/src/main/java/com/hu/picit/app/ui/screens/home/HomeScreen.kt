package com.hu.picit.app.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.model.SharedCategoryViewModel
import com.hu.picit.app.model.SharedFruitViewModel

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    sharedFruitViewModel: SharedFruitViewModel,
    modifier: Modifier = Modifier,
    sharedCartViewModel: SharedCartViewModel,
    sharedCategoryViewModel: SharedCategoryViewModel,
    navController: NavController
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            ResultScreen(
                navController = navController,
                sharedFruitViewModel = sharedFruitViewModel,
                locations = homeUiState.locationResponse,
                recommendedFruits = homeUiState.recommendedFruits,
                sharedCartViewModel = sharedCartViewModel,
                sharedCategoryViewModel = sharedCategoryViewModel
            )
        }
        is HomeUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}