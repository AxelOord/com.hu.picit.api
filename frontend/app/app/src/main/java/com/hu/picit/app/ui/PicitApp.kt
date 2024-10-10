package com.hu.picit.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hu.picit.app.ui.screens.home.HomeViewModel
import com.hu.picit.app.ui.screens.home.HomeScreen

@Composable
fun PicitApp() {
    Scaffold { contentPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val homeViewModel: HomeViewModel = viewModel()

            HomeScreen(
                homeUiState = homeViewModel.homeUiState,
                contentPadding = contentPadding
            )
        }
    }
}