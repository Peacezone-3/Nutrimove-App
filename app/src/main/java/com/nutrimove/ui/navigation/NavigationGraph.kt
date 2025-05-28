package com.nutrimove.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nutrimove.ui.screens.*

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "onboarding") {
        composable("onboarding") { OnboardingScreen(navController) }
        composable("nutrition") { NutritionScreen() }
        composable("training") { TrainingScreen() }
        composable("profile") { ProfileScreen() }
    }
}