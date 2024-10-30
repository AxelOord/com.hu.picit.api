package com.hu.picit.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.hu.picit.app.R

@Composable
fun ParallaxHeader() {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxWidth()) {
        // Background image with subtle parallax effect
        Image(
            painter = painterResource(id = R.drawable.header), // replace with your image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .graphicsLayer {
                    translationY = scrollState.value * 2f // Parallax effect
                }
        )

        // Overlay content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "Beste kwaliteit voor de beste prijs",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Dagelijks vers fruit",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: handle click */ },
            ) {
                Text(text = "Shop nu", color = Color.White)
            }
        }
    }
}