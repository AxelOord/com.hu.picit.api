package com.hu.picit.app.model

import com.squareup.moshi.Json

data class LocationResponse(
    val message: String,
    val status: Int,
    val data: List<LocationData>
)

data class LocationData(
    val type: String,
    val id: Int,
    val attributes: LocationAttributes
)

data class LocationAttributes(
    @Json(name = "name") val name: String
)

fun extractLocationNames(locationResponse: LocationResponse): List<String> {
    return locationResponse.data.map { it.attributes.name }
}