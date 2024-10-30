package com.hu.picit.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hu.picit.app.R
import com.hu.picit.app.model.sampleFruits
import com.hu.picit.app.ui.components.CategoriesRow
import com.hu.picit.app.ui.components.Category
import com.hu.picit.app.ui.components.FruitItem
import com.hu.picit.app.ui.components.LocationDropdown
import com.hu.picit.app.ui.components.OfferCard
import com.hu.picit.app.ui.components.ParallaxHeader
import com.hu.picit.app.ui.theme.PicitTheme

@Composable
fun ResultScreen(locations: List<String>, categories: List<String>, modifier: Modifier = Modifier) {
    var selectedLocation by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    val categoriesWithImage = categories.map { categoryFromApi ->
        Category(
            name = categoryFromApi,
            imageUrl = getPlaceholderImageUrl(categoryFromApi) // Assign placeholder
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState // Attach LazyListState to this LazyColumn
    ) {
        // First item: Parallax header
        item {
            ParallaxHeader(lazyListState = lazyListState)
        }

        // Remaining items with a white background
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // White background for content
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Column {
                    LocationDropdown(
                        locations = locations,
                        selectedLocation = selectedLocation,
                        onLocationSelected = { selectedLocation = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CategoriesRow(categories = categoriesWithImage)

                    Spacer(modifier = Modifier.height(24.dp))

                    //OfferCard()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Aanbevolen",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }

        // List of fruits or any other items with dividers
        items(sampleFruits()) { fruit ->
            FruitItem(fruit = fruit)
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    PicitTheme {
        ResultScreen(locations = listOf(), categories = listOf())
    }
}

fun getPlaceholderImageUrl(categoryName: String): String {
    return when (categoryName) {
        "Citrus" -> "https://example.com/citrus.jpg"
        "Bessen" -> "https://example.com/bessen.jpg"
        "Tropisch" -> "https://example.com/tropisch.jpg"
        "Steenvruchten" -> "https://example.com/steen.jpg"
        "Meloenen" -> "https://example.com/meloenen.jpg"
        else -> "https://example.com/default.jpg" // Default image for other categories
    }
}