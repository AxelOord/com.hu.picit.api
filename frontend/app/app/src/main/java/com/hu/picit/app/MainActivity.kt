package com.hu.picit.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hu.picit.app.ui.PicitApp
import com.hu.picit.app.ui.theme.PicitTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PicitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    PicitApp()
                }
            }
        }
    }
}