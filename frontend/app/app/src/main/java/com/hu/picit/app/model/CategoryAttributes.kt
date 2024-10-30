package com.hu.picit.app.model

import com.squareup.moshi.Json

data class CategoryResponse(
    @Json(name = "name") val name: String
)

fun extractCategoryNames(categoryResponse: ApiResponse<CategoryResponse>): List<String> {
    return categoryResponse.data.map { it.attributes.name }
}