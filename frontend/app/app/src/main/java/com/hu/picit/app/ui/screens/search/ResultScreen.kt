package com.hu.picit.app.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hu.picit.app.model.Fruit
import com.hu.picit.app.model.SharedFruitViewModel
import com.hu.picit.app.model.sampleFruits
import com.hu.picit.app.ui.components.FruitItem
import com.hu.picit.app.ui.theme.PicitTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    fruits: List<Fruit>,
    navController: NavController?,
    sharedFruitViewModel: SharedFruitViewModel,
    allFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val filters = listOf("Citrus", "Berries", "Tropical", "Stone Fruits", "Melons", "Pomes")
    val selectedStates = remember { mutableStateListOf(*Array(filters.size) { false }) }

    var selectedOption by remember { mutableStateOf("Populariteit") } // Default option

    var isSheetOpen by rememberSaveable() {
        mutableStateOf(false)
    }

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
                    IconButton(onClick = { navController?.navigate(Screen.Cart.route) }) {
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
                .background(White)
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            // Filter Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterButton(
                        text = "Filteren",
                        icon = Icons.Filled.FilterList,
                        onClick = { isSheetOpen = true}
                    )
                }
                item {
                    FilterButton(
                        text = "Sorteren",
                        icon = Icons.Filled.SwapVert,
                        onClick = { isSheetOpen = true}
                    )
                }

                filters.forEachIndexed { index, filter ->
                    item {
                        FilterButton(
                            text = filter,
                            isSelected = selectedStates[index],
                            onClick = { selectedStates[index] = !selectedStates[index] }
                        )
                    }
                }
            }

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 12.dp)
            ) {
                items(fruits.chunked(2)) { fruitPair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White, shape = RoundedCornerShape(12.dp))
                    ) {
                        fruitPair.forEach { fruit ->
                            FruitItem(
                                fruit = fruit,
                                allFavorite = allFavorite,
                                modifier = Modifier.weight(1f),
                                navController = navController!!,
                                sharedFruitViewModel = sharedFruitViewModel
                            )
                        }
                    }
                }
            }
        }

        if(isSheetOpen){
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Sorteren op",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val sortOptions =
                        listOf("Populariteit", "Prijs laag-hoog", "Prijs hoog-laag", "Rijpingsdatum")
                    sortOptions.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedOption == option),
                                onClick = {
                                    selectedOption = option
                                }
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun FilterButton(text: String, icon: ImageVector? = null, onClick: (() -> Unit)? = null, isSelected: Boolean = false) {
    Button(
        onClick = { onClick?.invoke() },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = text, color = Black)
        icon?.let {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = it, contentDescription = null, tint = Black)
        }

        if (isSelected) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = Icons.Filled.Close, contentDescription = null, tint = Black)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    PicitTheme {
        ResultScreen(fruits = sampleFruits(), null, SharedFruitViewModel(),false)
    }
}