// app/src/main/java/com/nutrimove/ui/screens/treinos/ExerciseDetailScreen.kt
package com.nutrimove.ui.screens.treinos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.R
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import com.nutrimove.ui.screens.treinos.generateSplit

@Composable
fun ExerciseDetailScreen(
    navController: NavController,
    dayIndex: Int,
    exIndex: Int
) {
    // 1) Read stored training-days count
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)

    // 2) Build split and pick the exercise
    val plan = remember(days) { generateSplit(days) }
    val ex = plan.getOrNull(dayIndex)
        ?.exercises?.getOrNull(exIndex)
        ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // Title
        Text(
            text = ex.name,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = Dimens.spacingMd)
        )

        // Image based on exercise name
        val imageRes = when {
            ex.name.contains("Supino", ignoreCase = true)       -> R.drawable.bench_press
            ex.name.contains("Agachamento", ignoreCase = true) -> R.drawable.squat
            ex.name.contains("Prensa de pernas", ignoreCase = true) -> R.drawable.leg_press
            ex.name.contains("Deadlift", ignoreCase = true) -> R.drawable.deadlift
            else                                              -> null
        }

        imageRes?.let { resId ->
            Image(
                painter = painterResource(id = resId),
                contentDescription = ex.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = Dimens.spacingMd)
            )
        } ?: Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = Dimens.spacingMd),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No image for “${ex.name}”",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Target muscle
        Text(
            text = "Músculo alvo: ${ex.targetMuscle}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Dimens.spacingSm)
        )

        // Tips
        Text(
            text = "Dicas:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Dimens.spacingXs)
        )
        ex.tips.forEach { tip ->
            Text(
                text = "• $tip",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = Dimens.spacingXs)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // AI Pessoal card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: AI callback */ },
            shape = RoundedCornerShape(Dimens.cornerDefault),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.spacingMd),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "AI Pessoal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(Dimens.spacingXs))
                    Text(
                        text = "Tire dúvidas comigo!",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.spacingMd))

        // “Sair” link
        Text(
            text = "Sair",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.popBackStack() }
                .padding(vertical = Dimens.spacingSm),
            textAlign = TextAlign.Center
        )
    }
}
