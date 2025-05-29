// app/src/main/java/com/nutrimove/ui/theme/Color.kt
package com.nutrimove.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary    = Color(0xFF4CAF50),
    onPrimary  = Color.White,
    background = Color(0xFFF9F9F9),
    surface    = Color.White,
    onSurface  = Color(0xFF212121),
)

val DarkColors = darkColorScheme(
    primary    = Color(0xFF81C784),
    onPrimary  = Color.Black,
    background = Color(0xFF121212),
    surface    = Color(0xFF1E1E1E),
    onSurface  = Color(0xFFE0E0E0),
)
