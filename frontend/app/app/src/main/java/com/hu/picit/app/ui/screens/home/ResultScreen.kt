package com.hu.picit.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.model.SharedCategoryViewModel
import com.hu.picit.app.model.SharedFruitViewModel
import com.hu.picit.app.model.sampleFruits
import com.hu.picit.app.ui.components.CategoriesRow
import com.hu.picit.app.ui.components.FruitItem
import com.hu.picit.app.ui.components.LocationDropdown
import com.hu.picit.app.ui.components.ParallaxHeader
import com.hu.picit.app.ui.theme.PicitTheme

@Composable
fun ResultScreen(
    navController: NavController,
    sharedFruitViewModel: SharedFruitViewModel,
    sharedCartViewModel: SharedCartViewModel,
    sharedCategoryViewModel: SharedCategoryViewModel,
    locations: List<String>,
    recommendedFruits: List<Fruit>,
    modifier: Modifier = Modifier
) {
    var selectedLocation by remember { mutableStateOf("Amsterdam") }
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState
    ) {
        item {
            ParallaxHeader(
                lazyListState = lazyListState,
                onCtaClicked = { navController?.navigate(Screen.Categories.route) }
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp)
            ) {
                Column {
                    LocationDropdown(
                        locations = locations,
                        selectedLocation = selectedLocation,
                        onLocationSelected = { selectedLocation = it },
                        onCartClicked = { navController?.navigate(Screen.Cart.route) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CategoriesRow(
                        sharedCategoryViewModel = sharedCategoryViewModel,
                        navController = navController!!
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    //OfferCard()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Aanbevolen",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }

        items(recommendedFruits.chunked(2)) { fruitPair ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp)
            ) {
                fruitPair.forEach { fruit ->
                    FruitItem(
                        fruit = fruit,
                        allFavorite = false,
                        modifier = Modifier
                            .weight(1f),
                        navController = navController!!,
                        sharedFruitViewModel = sharedFruitViewModel,
                        sharedCartViewModel = sharedCartViewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    PicitTheme {
        ResultScreen(locations = listOf(),  navController = NavController(LocalContext.current), sharedFruitViewModel = SharedFruitViewModel(), sharedCartViewModel = SharedCartViewModel(), sharedCategoryViewModel = SharedCategoryViewModel(), recommendedFruits = sampleFruits())
    }
}