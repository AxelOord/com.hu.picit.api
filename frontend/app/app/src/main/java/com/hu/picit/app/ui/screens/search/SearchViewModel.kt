package com.hu.picit.app.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.extractFruits
import com.hu.picit.app.network.ApiServiceProvider
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SearchUiState {
    data class Success(val fruits: List<Fruit>) : SearchUiState
    data object Error : SearchUiState
    data object Loading : SearchUiState
}

class SearchViewModel : ViewModel() {
    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.Loading)

    init {
        getFruits()
    }

    private fun getFruits() {
        viewModelScope.launch {
            searchUiState = SearchUiState.Loading
            searchUiState = try {
                val fruitResponse = ApiServiceProvider.retrofitService.getFruits()

                SearchUiState.Success(
                    extractFruits(fruitResponse)
                )
            } catch (e: IOException) {
                SearchUiState.Error
            } catch (e: HttpException) {
                SearchUiState.Error
            }
        }
    }
}