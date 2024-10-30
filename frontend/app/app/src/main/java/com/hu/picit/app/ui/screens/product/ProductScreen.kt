package com.hu.picit.app.ui.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hu.picit.app.model.SharedFruitViewModel

@Composable
fun ProductScreen(
    productUiState: ProductUiState,
    modifier: Modifier = Modifier,
    navController: NavController?,
    sharedFruitViewModel: SharedFruitViewModel
) {
    when (productUiState) {
        is ProductUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ProductUiState.Success -> {
            ResultScreen(navController = navController, sharedFruitViewModel = sharedFruitViewModel)
        }
        is ProductUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "An error occurred while loading the product.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}