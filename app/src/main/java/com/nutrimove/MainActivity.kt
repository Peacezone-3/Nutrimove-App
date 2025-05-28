package com.nutrimove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.components.BottomNavBar
import com.nutrimove.ui.navigation.NavigationGraph
import com.nutrimove.ui.theme.NutriMoveTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMoveApp()
        }
    }
}

@Composable
fun NutriMoveApp() {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val hasCompletedOnboarding by prefs.onboardingCompletedFlow.collectAsState(initial = false)
    val navController = rememberNavController()

    // ⚠️ Resetar o onboarding ao iniciar a app (para testes)
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        prefs.resetOnboarding()
    }

    NutriMoveTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(
                    navController = navController,
                    hasCompletedOnboarding = hasCompletedOnboarding
                )
            }
        }
    }
}
