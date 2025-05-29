package com.nutrimove.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Nutrition : NavItem("nutrition", "Nutrição", Icons.Default.Restaurant)
    object Training  : NavItem("training",  "Treino",    Icons.Default.FitnessCenter)
    object Profile   : NavItem("profile",   "Perfil",    Icons.Default.Person)
}
