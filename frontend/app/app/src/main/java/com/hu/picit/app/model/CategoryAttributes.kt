package com.hu.picit.app.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.picit.app.network.ApiServiceProvider
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CategoryAttributes(
    @Json(name = "name") val name: String,
    @Json(name = "img") val img: String
)

class SharedCategoryViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        fetchCategories()
    }

    fun deselectAllCategories(except: Category? = null) {
        _categories.value = _categories.value.map { category ->
            // Only keep selected state if it matches the 'except' category
            if (category == except) category else category.copy(selected = MutableStateFlow(false))
        }
    }
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = ApiServiceProvider.retrofitService.getCategories()
                // Log response for debugging
                println("API Response: $response")

                // Check and process response
                _categories.value = extractCategories(response) ?: emptyList() // Ensure the extracted list is not null
            } catch (e: Exception) {
                // Handle exceptions and log for debugging
                println("Error fetching categories: ${e.message}")
            }
        }
    }
}

fun extractCategories(categoryResponse: ApiResponse<CategoryAttributes>): List<Category> {
    return categoryResponse.data.map {
        Category(
            id = it.id,
            name = it.attributes.name,
            img = it.attributes.img,
            selected = MutableStateFlow(false)
        )
    }
}

data class Category(
    val id: Int,
    val name: String,
    val img: String,
    val selected: MutableStateFlow<Boolean> = MutableStateFlow(false)
)
