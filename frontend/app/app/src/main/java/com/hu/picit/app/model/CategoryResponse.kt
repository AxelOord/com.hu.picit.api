package com.hu.picit.app.model

import com.squareup.moshi.Json

data class CategoryResponse(
    val message: String,
    val status: Int,
    val data: List<CategoryData>
)

data class CategoryData(
    val type: String,
    val id: Int,
    val attributes: CategoryAttributes
)

data class CategoryAttributes(
    @Json(name = "name") val name: String
)

fun extractCategoryNames(categoryResponse: CategoryResponse): List<String> {
    return categoryResponse.data.map { it.attributes.name }
}