package com.nutrimove.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

// Onboarding screens
import com.nutrimove.ui.screens.onboarding.NameEntryScreen
import com.nutrimove.ui.screens.onboarding.OnBoardingScreen

// Nutrition
import com.nutrimove.ui.screens.nutrition.NutritionScreen

// Training (“Treinos”) feature
import com.nutrimove.ui.screens.treinos.TrainingScreen
import com.nutrimove.ui.screens.treinos.DayPlanScreen
import com.nutrimove.ui.screens.treinos.ExerciseDetailScreen

// Profile screens
import com.nutrimove.ui.screens.profile.ProfileScreen
import com.nutrimove.ui.screens.profile.ProfilePersonalScreen  // <- Corrigido aqui
import com.nutrimove.ui.screens.profile.ProgressionScreen
import com.nutrimove.ui.screens.profile.StatisticsScreen
import com.nutrimove.ui.screens.profile.TipsScreen
import com.nutrimove.ui.screens.profile.TrainingDaysScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    hasCompletedOnboarding: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (hasCompletedOnboarding) "training" else "onboarding_name"
    ) {
        // 1) Name entry
        composable("onboarding_name") {
            NameEntryScreen(navController) { name ->
                navController.navigate("onboarding_main/$name")
            }
        }

        // 2) Main onboarding flow
        composable(
            "onboarding_main/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            OnBoardingScreen(navController, userName = name)
        }

        // 3) Main tabs
        composable("nutrition") { NutritionScreen() }
        composable("training")  { TrainingScreen(navController) }
        composable("profile")   { ProfileScreen(navController) }

        // 4) Perfil sub-screens
        composable("profile_personal")    { ProfilePersonalScreen(navController) } // <- Corrigido
        composable("profile_progress")    { ProgressionScreen(navController) }
        composable("profile_statistics")  { StatisticsScreen(navController) }
        composable("profile_tips")        { TipsScreen(navController) }
        composable("training_days")       { TrainingDaysScreen(navController) }

        // 5) Drill-down: Day plan (Treinos)
        composable(
            "dayPlan/{dayIndex}",
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments!!.getInt("dayIndex")
            DayPlanScreen(navController, dayIndex)
        }

        // 6) Drill-down: Exercise detail
        composable(
            "exerciseDetail/{dayIndex}/{exIndex}",
            arguments = listOf(
                navArgument("dayIndex") { type = NavType.IntType },
                navArgument("exIndex")   { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val d = backStackEntry.arguments!!.getInt("dayIndex")
            val e = backStackEntry.arguments!!.getInt("exIndex")
            ExerciseDetailScreen(navController, dayIndex = d, exIndex = e)
        }
    }
}
