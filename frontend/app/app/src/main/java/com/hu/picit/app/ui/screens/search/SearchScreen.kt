package com.hu.picit.app.ui.screens.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hu.picit.app.model.SharedFruitViewModel

@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    modifier: Modifier = Modifier,
    sharedFruitViewModel: SharedFruitViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    allFavorite: Boolean = false
) {
    when (searchUiState) {
        is SearchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is SearchUiState.Success -> {
            ResultScreen(sharedFruitViewModel = sharedFruitViewModel, fruits = searchUiState.fruits, navController = navController, allFavorite = allFavorite,)
        }
        is SearchUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}