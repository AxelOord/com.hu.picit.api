package com.hu.picit.app.model

import com.squareup.moshi.Json

data class LocationAttributes(
    @Json(name = "name") val name: String
)

fun extractLocationNames(locationResponse: ApiResponse<LocationAttributes>): List<String> {
    return locationResponse.data.map { it.attributes.name }
}