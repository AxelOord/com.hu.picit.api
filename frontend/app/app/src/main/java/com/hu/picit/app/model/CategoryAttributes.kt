package com.hu.picit.app.model

import com.squareup.moshi.Json

data class CategoryAttributes(
    @Json(name = "name") val name: String
)

fun extractCategoryNames(categoryResponse: ApiResponse<CategoryAttributes>): List<String> {
    return categoryResponse.data.map { it.attributes.name }
}