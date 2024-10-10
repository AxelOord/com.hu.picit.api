package com.hu.picit.app.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            ResultScreen(locations = homeUiState.locationResponse, categories = homeUiState.categoryResponse)
        }
        is HomeUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}