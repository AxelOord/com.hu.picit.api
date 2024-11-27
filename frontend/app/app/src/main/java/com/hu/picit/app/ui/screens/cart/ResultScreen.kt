package com.hu.picit.app.ui.screens.cart

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.cloudinary.transformation.resize.Resize
import com.hu.picit.app.R
import com.hu.picit.app.model.CartItem
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.model.sampleFruits
import com.hu.picit.app.network.cloudinary
import com.hu.picit.app.ui.components.TopBar
import com.hu.picit.app.ui.theme.PicitTheme

@Composable
fun ResultScreen(
    navController: NavController,
    sharedCartViewModel: SharedCartViewModel
) {
    // Collect the cart items as state to ensure reactivity
    val cartItemsState = sharedCartViewModel.cartItems.collectAsState()
    val cartItems = cartItemsState.value

    Scaffold(
        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            CartSummary(total = cartItems.sumOf { it.fruit.pricePerKg * it.quantity })
        },
        containerColor = White
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .padding(innerPadding)

        ) {
            items(cartItems) { item -> // Pass the reactive list here
                CartItemRow(item, sharedCartViewModel)
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, sharedCartViewModel: SharedCartViewModel) {
    val img = cloudinary.image {
        publicId(item.fruit.imageUrl)
        resize(Resize.fill {
            width(200)
            height(200)
        })
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(img.generate()))
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = "Apple",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(18.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = item.fruit.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = item.quantityLabel, style = MaterialTheme.typography.bodySmall, color = Gray)
            QuantityPicker(
                fruit = item.fruit,
                onQuantityChange = {  },
                sharedCartViewModel = sharedCartViewModel
            )
        }
        Text(
            text = "$${item.fruit.pricePerKg * item.quantity}",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun QuantityPicker(fruit: Fruit, sharedCartViewModel: SharedCartViewModel, onQuantityChange: (Int) -> Unit) {
    // Collect the cart items as state to observe changes
    val cartItemsState = sharedCartViewModel.cartItems.collectAsState()
    val cartItems = cartItemsState.value
    val fruitQuantityInCart = cartItems.find { it.fruit.id == fruit.id }?.quantity ?: 0

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        IconButton(
            onClick = {
                sharedCartViewModel.decrementFruitFromCart(fruit)
                onQuantityChange(fruitQuantityInCart - 1)
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease quantity"
            )
        }
        Text(
            text = fruitQuantityInCart.toString(),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = {
                sharedCartViewModel.incrementFruitFromCart(fruit)
                onQuantityChange(fruitQuantityInCart + 1)
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity"
            )
        }
    }
}


@Composable
fun CartSummary(total: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Totaal", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "$$total", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle order placement */ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Plaats bestelling")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    val items = listOf(
        CartItem(sampleFruits()[0], 2, "1 kg"),
        CartItem(sampleFruits()[1], 1, "1 dozen"),
        CartItem(sampleFruits()[2], 3, "500 g")
    )
    PicitTheme {
        ResultScreen(navController = NavController(LocalContext.current), sharedCartViewModel = SharedCartViewModel())
    }
}
