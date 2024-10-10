package com.hu.picit.app.model

data class Fruit(
    val name: String,
    val countryOfOrigin: String,
    val pricePerKg: Double
)

fun sampleFruits(): List<Fruit> {
    return listOf(
        Fruit(name = "Appels", countryOfOrigin = "Nederland", pricePerKg = 2.99),
        Fruit(name = "Bananen", countryOfOrigin = "Ecuador", pricePerKg = 1.49),
        Fruit(name = "Aardbeien", countryOfOrigin = "België", pricePerKg = 3.99),
        Fruit(name = "Bessen", countryOfOrigin = "India", pricePerKg = 2.59),
        Fruit(name = "Citroenen", countryOfOrigin = "Australië", pricePerKg = 1.39),
        Fruit(name = "Perziken", countryOfOrigin = "Japan", pricePerKg = 3.99)
    )
}