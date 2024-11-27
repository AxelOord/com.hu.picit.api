package com.hu.picit.app.ui.screens.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hu.picit.app.model.Category
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.model.SharedCategoryViewModel
import com.hu.picit.app.model.SharedFruitViewModel

@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    sharedFruitViewModel: SharedFruitViewModel,
    sharedCartViewModel: SharedCartViewModel,
    sharedCategoryViewModel: SharedCategoryViewModel,
    navController: NavController,
    allFavorite: Boolean = false
) {
    when (searchUiState) {
        is SearchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is SearchUiState.Success -> {
            ResultScreen(sharedFruitViewModel = sharedFruitViewModel, fruits = searchUiState.fruits, navController = navController, sharedCartViewModel = sharedCartViewModel, sharedCategoryViewModel = sharedCategoryViewModel, searchViewModel = searchViewModel, allFavorite = allFavorite,)
        }
        is SearchUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}