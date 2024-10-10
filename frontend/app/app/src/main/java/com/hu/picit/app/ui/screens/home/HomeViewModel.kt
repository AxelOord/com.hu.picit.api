package com.hu.picit.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.picit.app.model.extractCategoryNames
import com.hu.picit.app.model.extractLocationNames
import com.hu.picit.app.network.ApiServiceProvider
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState {
    data class Success(val locationResponse: List<String>, val categoryResponse: List<String>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel : ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                val locationResponse = ApiServiceProvider.retrofitService.getLocations()
                val categoryResponse = ApiServiceProvider.retrofitService.getCategories()

                HomeUiState.Success(
                    extractLocationNames(locationResponse),
                    extractCategoryNames(categoryResponse)
                )
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

}