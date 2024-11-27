package com.hu.picit.app.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.cloudinary.transformation.resize.Resize
import com.hu.picit.app.R
import com.hu.picit.app.model.Category
import com.hu.picit.app.model.SharedCategoryViewModel
import com.hu.picit.app.network.cloudinary

@Composable
fun CategoriesRow(
    navController: NavController,
    sharedCategoryViewModel: SharedCategoryViewModel,
    modifier: Modifier = Modifier
) {
    var categories = sharedCategoryViewModel.categories.collectAsState().value
    val firstRowCount = when (categories.size) {
        in 5..Int.MAX_VALUE -> 2
        4 -> 2
        3 -> 1
        else -> categories.size
    }

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
                        category = category,
                        navController = navController,
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
                        category = category,
                        navController = navController,
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
fun CategoryItem(
    category: Category,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
                .background(Color.LightGray)
                .clickable(
                    onClick = {
                        category.selected.value = true
                        navController.navigate(Screen.Categories.route)
                    }
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            val img = cloudinary.image {
                publicId(category.img)
                resize(Resize.fill {
                    width(300)
                    height(200)
                })
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(img.generate()))
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Adjust the height to control the gradient's reach
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                            startY = 0f,
                            endY = 150f // Adjust for gradient spread
                        )
                    )
            )

            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontSize = 18.sp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}
