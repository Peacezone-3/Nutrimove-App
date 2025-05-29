// app/src/main/java/com/nutrimove/ui/theme/NutriMoveTheme.kt
package com.nutrimove.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NutriMoveTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme  = colors,
        typography   = AppTypography,
        shapes       = AppShapes,
        content      = content
    )
}
