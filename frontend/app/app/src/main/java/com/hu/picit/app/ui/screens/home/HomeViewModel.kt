package com.hu.picit.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.extractCategoryNames
import com.hu.picit.app.model.extractFruits
import com.hu.picit.app.model.extractLocationNames
import com.hu.picit.app.network.ApiServiceProvider
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.log

sealed interface HomeUiState {
    data class Success(val locationResponse: List<String>, val categoryResponse: List<String>, val recommendedFruits: List<Fruit>) : HomeUiState
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
                val fruitResponse = ApiServiceProvider.retrofitService.getRecommendedFruits()

                HomeUiState.Success(
                    extractLocationNames(locationResponse),
                    extractCategoryNames(categoryResponse),
                    extractFruits(fruitResponse)
                )
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

}