package com.hu.picit.app.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.hu.picit.app.ui.components.FruitItem
import com.hu.picit.app.ui.components.LocationDropdown
import com.hu.picit.app.ui.components.OfferCard
import com.hu.picit.app.ui.theme.PicitTheme

@Composable
fun ResultScreen(locations: List<String>, categories: List<String>, modifier: Modifier = Modifier) {
    var selectedLocation by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 60.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        item {
            LocationDropdown(
                locations = locations,
                selectedLocation = selectedLocation,
                onLocationSelected = { selectedLocation = it }
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            CategoriesRow(categories = categories)
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            OfferCard()
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            Text(
                text = "Aanbevolen",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
            )
        }
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