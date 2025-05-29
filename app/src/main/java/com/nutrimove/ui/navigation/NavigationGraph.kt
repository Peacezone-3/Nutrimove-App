// app/src/main/java/com/nutrimove/ui/navigation/NavigationGraph.kt
package com.nutrimove.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nutrimove.ui.theme.NutriMoveTheme
import com.nutrimove.ui.screens.nutrition.AddMealPlanScreen
import com.nutrimove.ui.screens.nutrition.NutritionDetailScreen
import com.nutrimove.ui.screens.nutrition.NutritionListScreen
import com.nutrimove.ui.screens.onboarding.NameEntryScreen
import com.nutrimove.ui.screens.onboarding.OnBoardingScreen
import com.nutrimove.ui.screens.profile.*
import com.nutrimove.ui.screens.treinos.DayPlanScreen
import com.nutrimove.ui.screens.treinos.ExerciseDetailScreen
import com.nutrimove.ui.screens.treinos.TrainingScreen

@Composable
fun AppNavHost(startOnboarding: Boolean) {
    val navController = rememberNavController()

    NutriMoveTheme {
        Scaffold(
            bottomBar = {
                val currentRoute = navController
                    .currentBackStackEntryAsState().value
                    ?.destination
                    ?.route

                if (currentRoute in listOf(
                        NavItem.Nutrition.route,
                        NavItem.Training.route,
                        NavItem.Profile.route
                    )
                ) {
                    BottomNavBar(navController, currentRoute)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController    = navController,
                startDestination = if (startOnboarding) "onboarding_name" else NavItem.Nutrition.route,
                modifier         = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // ── Onboarding ──
                composable("onboarding_name") {
                    // now only takes onNext, which receives the typed name
                    NameEntryScreen { typedName ->
                        // navigate into the detailed onboarding flow, passing the name
                        navController.navigate("onboarding_flow/$typedName")
                    }
                }
                composable(
                    route = "onboarding_flow/{userName}",
                    arguments = listOf(
                        navArgument("userName") { type = NavType.StringType }
                    )
                ) { backStack ->
                    val userName = backStack.arguments?.getString("userName") ?: ""
                    OnBoardingScreen(
                        navController = navController,
                        userName      = userName
                    )
                }

                // ── Nutrition ──
                composable(NavItem.Nutrition.route) {
                    NutritionListScreen(navController)
                }
                composable(
                    route = "nutrition_detail/{planIndex}",
                    arguments = listOf(navArgument("planIndex") {
                        type = NavType.IntType
                    })
                ) { backStack ->
                    val idx = backStack.arguments?.getInt("planIndex") ?: 0
                    NutritionDetailScreen(navController, idx)
                }
                composable("add_plan") {
                    AddMealPlanScreen(navController)
                }

                // ── Training ──
                composable(NavItem.Training.route) {
                    TrainingScreen(navController)
                }
                composable(
                    route = "day_plan/{dayIndex}",
                    arguments = listOf(navArgument("dayIndex") {
                        type = NavType.IntType
                    })
                ) { backStack ->
                    val dayIndex = backStack.arguments?.getInt("dayIndex") ?: 0
                    DayPlanScreen(navController, dayIndex)
                }
                composable(
                    route = "exercise_detail/{dayIdx}/{exIdx}",
                    arguments = listOf(
                        navArgument("dayIdx") { type = NavType.IntType },
                        navArgument("exIdx")  { type = NavType.IntType }
                    )
                ) { backStack ->
                    val dayIdx = backStack.arguments?.getInt("dayIdx") ?: 0
                    val exIdx  = backStack.arguments?.getInt("exIdx")  ?: 0
                    ExerciseDetailScreen(navController, dayIdx, exIdx)
                }

                // ── Profile ──
                composable(NavItem.Profile.route) {
                    ProfileMainScreen(navController)
                }
                composable("profile_personal") {
                    ProfilePersonalScreen(navController)
                }
                composable("profile_personal_edit") {
                    PersonalDataScreen(navController)
                }
                composable("profile_training_days") {
                    TrainingDaysScreen(navController)
                }
                composable("profile_progression") {
                    ProgressionScreen(navController)
                }
                composable("profile_statistics") {
                    StatisticsScreen(navController)
                }
                composable("profile_tips") {
                    TipsScreen(navController)
                }
                composable("profile_trabalhe_conosco") {
                    TrabalheConoscoScreen(navController)
                }
            }
        }
    }
}
