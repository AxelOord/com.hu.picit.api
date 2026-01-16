package com.hu.picit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun QuantityPicker(
    label: String = "Hoeveelheid",
    options: List<String> = listOf("250 g", "500 g", "1 kg", "2 kg"),
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    testTag: String = "quantityPicker"
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .testTag(testTag)
            .semantics { contentDescription = "QuantityPicker" }
            .border(1.dp, LightGray, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(color = Gray, fontSize = 16.sp),
            modifier = Modifier.testTag("$testTag-label")
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.testTag("$testTag-selectedRow")
        ) {
            Text(
                text = selectedOption,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .testTag("$testTag-selectedValue")
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle dropdown",
                tint = Black,
                modifier = Modifier.testTag("$testTag-toggleIcon")
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(White)
                .testTag("$testTag-menu")
        ) @androidx.compose.runtime.Composable {
        options.forEach { option ->
            DropdownMenuItem(
                onClick = {
                    onOptionSelected(option)
                    expanded = false
                },
                text = { Text(text = option) },
                modifier = Modifier.testTag("$testTag-option-$option")
            )
        }
    }
    }
}