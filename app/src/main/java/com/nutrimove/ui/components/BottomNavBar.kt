package com.nutrimove.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Nutrition : Screen("nutrition", Icons.Default.Restaurant, "Nutrição")
    object Training : Screen("training", Icons.Default.FitnessCenter, "Treino")
    object Profile : Screen("profile", Icons.Default.Person, "Perfil")
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(Screen.Nutrition, Screen.Training, Screen.Profile)
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}
