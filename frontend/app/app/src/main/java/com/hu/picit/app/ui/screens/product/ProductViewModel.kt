package com.hu.picit.app.ui.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.sampleFruits
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data class Success(val fruit: Fruit) : ProductUiState
    data object Error : ProductUiState
    data object Loading : ProductUiState
}

class ProductViewModel : ViewModel() {
    var productUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)

    init {
        getProduct()
    }

    private fun getProduct() {
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = ProductUiState.Success(sampleFruits()[0])
        }
    }
}