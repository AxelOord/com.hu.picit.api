package com.hu.picit.app.model

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedFruitViewModel : ViewModel() {
    var selectedFruit: Fruit = sampleFruits()[0]
}

class SharedCartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun getFruitQuantityInCart(fruit: Fruit): Int {
        return _cartItems.value.find { it.fruit.id == fruit.id }?.quantity ?: 0
    }

    fun decrementFruitFromCart(fruit: Fruit, quantity: Int = 1) {
        _cartItems.value = _cartItems.value.mapNotNull { cartItem ->
            if (cartItem.fruit.id == fruit.id) {
                val newQuantity = cartItem.quantity - quantity
                if (newQuantity > 0) {
                    cartItem.copy(quantity = newQuantity)
                } else {
                    null // Remove item if quantity goes to 0
                }
            } else {
                cartItem
            }
        }.sortedBy { it.fruit.name }
    }

    fun incrementFruitFromCart(fruit: Fruit, quantity: Int = 1) {
        _cartItems.value = _cartItems.value.map { cartItem ->
            if (cartItem.fruit.id == fruit.id) {
                cartItem.copy(quantity = cartItem.quantity + quantity)
            } else {
                cartItem
            }
        }
    }

    fun addToCart(fruit: Fruit, quantity: Int = 1, quantityLabel: String) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.fruit.id == fruit.id }

        if (existingItem != null) {
            incrementFruitFromCart(fruit, quantity)
        } else {
            _cartItems.value = currentItems + CartItem(fruit, quantity, quantityLabel)
            _cartItems.value = _cartItems.value.sortedBy { it.fruit.name }
        }
    }
}

data class CartItem(
    val fruit: Fruit,
    var quantity: Int,
    val quantityLabel: String
)

data class FruitAttributes(
    @Json(name = "name") val name: String,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "price") val pricePerKg: Double,
    @Json(name = "countryOfOrigin") val countryOfOrigin: String,
    @Json(name = "img") val img: String
)

fun extractFruits(fruitResponse: ApiResponse<FruitAttributes>): List<Fruit> {
    return fruitResponse.data.map {
        Fruit(
            id = it.id,
            name = it.attributes.name,
            countryOfOrigin = it.attributes.countryOfOrigin,
            pricePerKg = it.attributes.pricePerKg,
            imageUrl = it.attributes.img,
            isFavorite = false
        )
    }
}

data class Fruit(
    val id: Int,
    val name: String,
    val countryOfOrigin: String,
    val pricePerKg: Double,
    val imageUrl: String,
    var isFavorite: Boolean = false
)

fun sampleFruits(): List<Fruit> {
    return listOf(
        Fruit(id = 1, name = "Appels", countryOfOrigin = "Nederland", pricePerKg = 2.99, imageUrl = "Apple"),
        Fruit(id = 2, name = "Bananen", countryOfOrigin = "Ecuador", pricePerKg = 1.49, imageUrl = "Banana"),
        Fruit(id = 3, name = "Aardbeien", countryOfOrigin = "België", pricePerKg = 3.99, imageUrl = "Strawberry"),
        Fruit(id = 4, name = "Bessen", countryOfOrigin = "India", pricePerKg = 2.59, imageUrl = "Berry"),
        Fruit(id = 5, name = "Druifjes", countryOfOrigin = "Australië", pricePerKg = 1.39, imageUrl = "Grape"),
        Fruit(id = 6, name = "Ananas", countryOfOrigin = "Japan", pricePerKg = 3.99, imageUrl = "Pineapple")
    )
}