package com.hu.picit.app.ui.screens.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.extractFruits
import com.hu.picit.app.model.sampleFruits
import com.hu.picit.app.network.ApiServiceProvider
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CartUiState {
    data class Success(val fruits: List<Fruit>) : CartUiState
    data object Error : CartUiState
    data object Loading : CartUiState
}

class CartViewModel : ViewModel() {
    var cartUiState: CartUiState by mutableStateOf(CartUiState.Loading)

    init {
        getFruits()
    }

    private fun getFruits() {
        viewModelScope.launch {
            cartUiState = CartUiState.Loading
            cartUiState = try {

                CartUiState.Success(sampleFruits())
            } catch (e: IOException) {
                CartUiState.Error
            } catch (e: HttpException) {
                CartUiState.Error
            }
        }
    }
}