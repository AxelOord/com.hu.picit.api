package com.hu.picit.app.ui.screens.product

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.cloudinary.transformation.resize.Resize
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.model.SharedFruitViewModel
import com.hu.picit.app.network.cloudinary
import com.hu.picit.app.ui.components.QuantityPicker
import com.hu.picit.app.ui.components.TopBar
import com.hu.picit.app.ui.theme.PicitTheme

@Composable
fun ResultScreen(
    sharedFruitViewModel: SharedFruitViewModel,
    navController: NavController,
    sharedCartViewModel: SharedCartViewModel
) {
    val fruit = sharedFruitViewModel.selectedFruit
    var selectedQuantity by remember { mutableStateOf("1 kg") }

    val cartItems by sharedCartViewModel.cartItems.collectAsState()

    val fruitQuantityInCart = cartItems.find { it.fruit.id == fruit.id }?.quantity ?: 0

    Scaffold(
        topBar = {
            TopBar(navController)
        },
        containerColor = White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            val img = cloudinary.image {
                publicId(fruit.imageUrl)
                resize(Resize.fill {
                    width(200)
                    height(200)
                })
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(img.generate()))
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = fruit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(18.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = fruit.countryOfOrigin,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Text(
                text = fruit.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$${fruit.pricePerKg}/kg",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            QuantityPicker(
                selectedOption = selectedQuantity,
                onOptionSelected = { selectedQuantity = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { sharedCartViewModel.addToCart(fruit, quantityLabel = selectedQuantity) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                ) {
                    if (sharedCartViewModel.getFruitQuantityInCart(fruit) > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            IconButton(
                                onClick = { sharedCartViewModel.decrementFruitFromCart(fruit) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease quantity"
                                )
                            }
                            Text(
                                text = sharedCartViewModel.getFruitQuantityInCart(fruit).toString(),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(
                                onClick = { sharedCartViewModel.incrementFruitFromCart(fruit) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase quantity"
                                )
                            }
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to Cart",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Voeg toe", color = Color.White)
                    }
                }
                IconButton(
                    onClick = { /* Favorite logic */ },
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.Gray, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Fresh and juicy apples, perfect for a healthy snack.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "View More",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                modifier = Modifier.clickable { /* Navigate to details */ }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    PicitTheme {
        ResultScreen(navController = NavController(LocalContext.current), sharedFruitViewModel = SharedFruitViewModel(), sharedCartViewModel = SharedCartViewModel())
    }
}