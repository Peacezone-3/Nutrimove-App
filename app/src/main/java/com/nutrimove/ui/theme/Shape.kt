// app/src/main/java/com/nutrimove/ui/theme/Shape.kt
package com.nutrimove.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val AppShapes = Shapes(
    small  = RoundedCornerShape(Dimens.cornerSmall),
    medium = RoundedCornerShape(Dimens.cornerDefault),
    large  = RoundedCornerShape(Dimens.cornerDefault)
)
