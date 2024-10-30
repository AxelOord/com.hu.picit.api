package com.hu.picit.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.hu.picit.app.R

@Composable
fun CategoriesRow(categories: List<Category>, modifier: Modifier = Modifier) {
    val firstRowCount = when (categories.size) {
        in 5..Int.MAX_VALUE -> 2
        4 -> 2
        3 -> 1
        else -> categories.size // For 2 or fewer categories
    }
    val secondRowCount = categories.size - firstRowCount

    val firstRowCategories = categories.take(firstRowCount)
    val secondRowCategories = categories.drop(firstRowCount)

    Column(modifier = modifier.fillMaxWidth()) {
        if (firstRowCategories.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp) // Add space between items
            ) {
                firstRowCategories.forEach { category ->
                    CategoryItem(
                        name = category.name,
                        imageUrl = category.imageUrl,
                        modifier = Modifier
                            .weight(1f)
                            .size(height = 120.dp, width = 160.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (secondRowCategories.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp) // Add space between items
            ) {
                secondRowCategories.forEach { category ->
                    CategoryItem(
                        name = category.name,
                        imageUrl = category.imageUrl,
                        modifier = Modifier
                            .weight(1f)
                            .size(height = 100.dp, width = 160.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, imageUrl: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray, shape = RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                //contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontSize = 18.sp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}

data class Category(val name: String, val imageUrl: String)