// app/src/main/java/com/nutrimove/ui/screens/treinos/DayPlanScreen.kt
package com.nutrimove.ui.screens.treinos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens

@Composable
fun DayPlanScreen(
    navController: NavController,
    dayIndex: Int
) {
    // Load saved training days and generate split
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)
    val plan = remember(days) { generateSplit(days) }
    val day  = plan.getOrNull(dayIndex) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // 1) Header: centered title
        Text(
            text      = day.title,
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.spacingMd),
            textAlign = TextAlign.Center
        )

        // 2) Scrollable exercise list
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            day.exercises.forEachIndexed { exIdx, ex ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.spacingXs)
                        .clickable {
                            // Navigate to detail screen
                            navController.navigate("exercise_detail/$dayIndex/$exIdx")
                        }
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { /* you can track completion here */ }
                    )
                    Spacer(Modifier.width(Dimens.spacingSm))
                    Column {
                        Text(
                            text       = ex.name,
                            style      = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text       = "${ex.sets} séries x ${ex.reps} repetições",
                            style      = MaterialTheme.typography.bodySmall,
                            color      = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Divider()
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // 3) AI Pessoal card (always visible)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: AI callback */ },
            shape     = RoundedCornerShape(Dimens.cornerDefault),
            border    = CardDefaults.outlinedCardBorder()
        ) {
            Box(
                modifier           = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.spacingMd),
                contentAlignment   = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text       = "AI Pessoal",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(Dimens.spacingXs))
                    Text(
                        text       = "Tire dúvidas comigo!",
                        style      = MaterialTheme.typography.bodySmall,
                        textAlign  = TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // 4) Sair link (always visible)
        Text(
            text      = "Sair",
            style     = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
            modifier  = Modifier
                .fillMaxWidth()
                .clickable { navController.popBackStack() }
                .padding(vertical = Dimens.spacingSm),
            textAlign = TextAlign.Center
        )
    }
}
