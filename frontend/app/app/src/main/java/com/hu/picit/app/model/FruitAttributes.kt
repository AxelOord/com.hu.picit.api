package com.hu.picit.app.model

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Json

class SharedFruitViewModel : ViewModel() {
    var selectedFruit: Fruit = sampleFruits()[0]
}

data class FruitAttributes(
    @Json(name = "name") val name: String,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "price") val pricePerKg: Double,
    //@Json(name = "countryOfOrigin") val countryOfOrigin: String,
    @Json(name = "img") val img: String
)

fun extractFruits(categoryResponse: ApiResponse<FruitAttributes>): List<Fruit> {
    return categoryResponse.data.map {
        Fruit(
            id = it.id,
            name = it.attributes.name,
            countryOfOrigin = "Unknown", // Set a default value or populate it if available
            pricePerKg = it.attributes.pricePerKg, // Set a default or populate if available in `FruitAttributes`
            imageUrl = it.attributes.img,
            isFavorite = false // Default favorite status, or adjust as needed
        )
    }
}

data class Fruit(
    val id: Int,
    val name: String,
    val countryOfOrigin: String,
    val pricePerKg: Double,
    val imageUrl: String, // Added field for image URL
    var isFavorite: Boolean = false // Added field for favorite status
)

fun sampleFruits(): List<Fruit> {
    return listOf(
        Fruit(id = 1, name = "Appels", countryOfOrigin = "Nederland", pricePerKg = 2.99, imageUrl = "https://example.com/appel.jpg"),
        Fruit(id = 2, name = "Bananen", countryOfOrigin = "Ecuador", pricePerKg = 1.49, imageUrl = "https://example.com/bananen.jpg"),
        Fruit(id = 3, name = "Aardbeien", countryOfOrigin = "België", pricePerKg = 3.99, imageUrl = "https://example.com/aardbeien.jpg"),
        Fruit(id = 4, name = "Bessen", countryOfOrigin = "India", pricePerKg = 2.59, imageUrl = "https://example.com/bessen.jpg"),
        Fruit(id = 5, name = "Citroenen", countryOfOrigin = "Australië", pricePerKg = 1.39, imageUrl = "https://example.com/citroenen.jpg"),
        Fruit(id = 6, name = "Perziken", countryOfOrigin = "Japan", pricePerKg = 3.99, imageUrl = "https://example.com/perziken.jpg")
    )
}