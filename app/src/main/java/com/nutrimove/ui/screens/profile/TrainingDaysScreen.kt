// app/src/main/java/com/nutrimove/ui/screens/profile/TrainingDaysScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun TrainingDaysScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val current by prefs.trainingDaysFlow.collectAsState(initial = 3)
    var selected by remember { mutableStateOf(current) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text      = "Alterar Dias de Treino",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(Dimens.spacingLg))

        // Picker buttons (2–6)
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMd),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            (2..6).forEach { dayCount ->
                val isSelected = dayCount == selected
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { selected = dayCount }
                ) {
                    Text(
                        text  = dayCount.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // Preview grid: scrollable, takes all remaining space
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
        ) {
            (1..selected)
                .chunked(2)
                .forEach { rowDays ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMd),
                        modifier              = Modifier.fillMaxWidth()
                    ) {
                        rowDays.forEach { dayCount ->
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.5f)
                                    .clickable {
                                        navController.navigate("day_plan/${dayCount - 1}")
                                    },
                                shape     = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Box(
                                    modifier         = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Dia $dayCount",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                        // no placeholder spacer
                    }
                }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // Save & exit button remains visible
        Button(
            onClick = {
                scope.launch { prefs.saveTrainingDays(selected) }
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.spacingMd)
        ) {
            Text("Guardar alterações")
        }
    }
}
