package com.hu.picit.app.ui.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.SharedFruitViewModel
import com.hu.picit.app.model.sampleFruits
import com.hu.picit.app.ui.components.CategoriesRow
import com.hu.picit.app.ui.components.Category
import com.hu.picit.app.ui.components.FruitItem
import com.hu.picit.app.ui.components.LocationDropdown
import com.hu.picit.app.ui.components.ParallaxHeader
import com.hu.picit.app.ui.screens.home.getPlaceholderImageUrl
import com.hu.picit.app.ui.theme.PicitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    sharedFruitViewModel: SharedFruitViewModel,
    navController: NavController?,
) {
    val fruit = sharedFruitViewModel.selectedFruit

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 12.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Gray
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            // Image Slider (Placeholder, add real image carousel as needed)
            Image(
                painter = rememberAsyncImagePainter(fruit.imageUrl),
                contentDescription = "Fruit Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Country of origin
            Text(
                text = fruit.countryOfOrigin,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            // Fruit name
            Text(
                text = fruit.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // Price
            Text(
                text = "$${fruit.pricePerKg}/kg",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quantity selector (Dropdown or Picker)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hoeveelheid",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 8.dp)
                )
                // Replace this DropdownMenu with actual quantity picker
                Text(
                    text = "1 kg",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add to Cart and Favorite Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Add to cart logic */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Voeg toe", color = Color.White)
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

            // Description
            Text(
                text = "Fresh and juicy apples, perfect for a healthy snack.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // View More
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
        ResultScreen(navController = null, sharedFruitViewModel = SharedFruitViewModel())
    }
}