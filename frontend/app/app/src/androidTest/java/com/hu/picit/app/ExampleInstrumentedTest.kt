package com.hu.picit.app

import PicitApp
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hu.picit.app.ui.components.QuantityPicker
import com.hu.picit.app.ui.theme.PicitTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.hu.picit.app.model.SharedCartViewModel
import com.hu.picit.app.ui.components.BottomBar


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shopFlow_canOpenFilters_andSelectCategory() {
        composeTestRule.setContent {
            PicitTheme {
                Surface(Modifier.fillMaxSize()) {
                    PicitApp()
                }
            }
        }

        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("Shop nu").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Shop nu").performClick()

        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("Filteren").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Filteren").performClick()

        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("Steen vruchten").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Steen vruchten").performClick()

        composeTestRule.onNodeWithText("Steen vruchten").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class QuantityPickerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun selectingOption_updatesSelectedValue() {
        composeTestRule.setContent {
            var selected by remember { mutableStateOf("250 g") }

            QuantityPicker(
                selectedOption = selected,
                onOptionSelected = { selected = it },
                testTag = "quantityPicker"
            )
        }

        composeTestRule.onNodeWithTag("quantityPicker-selectedValue", useUnmergedTree = true)
            .assertTextEquals("250 g")

        composeTestRule.onNodeWithTag("quantityPicker", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("quantityPicker-menu", useUnmergedTree = true).assertIsDisplayed()

        composeTestRule.onNodeWithTag("quantityPicker-option-1 kg", useUnmergedTree = true).performClick()

        composeTestRule.onNodeWithTag("quantityPicker-selectedValue", useUnmergedTree = true)
            .assertTextEquals("1 kg")

        composeTestRule.onNodeWithTag("quantityPicker-menu", useUnmergedTree = true)
            .assertDoesNotExist()
    }
}


@RunWith(AndroidJUnit4::class)
class BottomBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun bottomBar_showsAllTabs() {
        val vm = SharedCartViewModel()

        composeTestRule.setContent {
            val navController = rememberNavController()
            BottomBar(navController = navController, sharedCartViewModel = vm)
        }

        composeTestRule.onNodeWithText("Home", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Categorieën", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Favorieten", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Winkelwagen", useUnmergedTree = true).assertExists()
    }
}