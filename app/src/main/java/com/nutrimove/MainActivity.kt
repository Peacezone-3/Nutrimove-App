package com.nutrimove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme // Assuming NutriMoveTheme provides this
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nutrimove.ui.navigation.NavigationGraph
import com.nutrimove.ui.components.BottomNavBar
import com.nutrimove.ui.theme.NutriMoveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMoveApp() // Encapsulate the app's root composable
        }
    }
}

@Composable
fun NutriMoveApp() {
    NutriMoveTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavBar(navController = navController) }
        ) { innerPadding -> // This lambda receives PaddingValues
            // Apply the padding provided by Scaffold to prevent content
            // from being drawn under the bottom bar or other Scaffold elements.
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(navController = navController)
            }
        }
    }
}