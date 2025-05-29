// app/src/main/java/com/nutrimove/ui/screens/profile/ProgressionScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProgressionScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val scope   = rememberCoroutineScope()

    // State flows
    val goalFlow   by prefs.goalWeightFlow.collectAsState(initial = null)
    val entriesMap by prefs.weightEntriesFlow.collectAsState(initial = emptyMap())
    val goalWeight = goalFlow

    // Local form state
    var goalInput   by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }

    // Flatten & sort
    val flatEntries = remember(entriesMap) {
        entriesMap.flatMap { (date, weights) ->
            weights.map { w -> date to w }
        }.sortedBy { it.first }
    }

    // Metrics
    val firstWeight = flatEntries.firstOrNull()?.second
    val lastWeight  = flatEntries.lastOrNull()?.second
    val totalChange = remember(firstWeight, lastWeight) {
        if (firstWeight != null && lastWeight != null) lastWeight - firstWeight else null
    }
    val diffGoal = remember(lastWeight, goalWeight) {
        if (lastWeight != null && goalWeight != null) goalWeight - lastWeight else null
    }

    // Helper rendered as composable
    @Composable
    fun StatItem(label: String, value: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.bodySmall)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // Header
        Text(
            text      = "Progressão de Peso",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(Dimens.spacingMd))

        if (goalWeight == null) {
            // Goal Setter Card
            Card(
                modifier  = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(Dimens.spacingMd)) {
                    Text("Defina sua meta", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = goalInput,
                        onValueChange = { goalInput = it },
                        label         = { Text("Meta de peso (kg)") },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            goalInput.toFloatOrNull()?.let { w ->
                                scope.launch { prefs.saveGoalWeight(w) }
                            }
                        },
                        enabled = goalInput.toFloatOrNull() != null,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Definir")
                    }
                }
            }
        } else {
            // Summary Card
            Card(
                modifier  = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier             = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.spacingMd),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatItem("Início", firstWeight?.let { "%.1fkg".format(it) } ?: "--")
                    StatItem("Atual", lastWeight?.let { "%.1fkg".format(it) } ?: "--")
                    StatItem("Δ Total", totalChange?.let { "%+.1fkg".format(it) } ?: "--")
                    StatItem("Até Meta", diffGoal?.let { "%+.1fkg".format(it) } ?: "--")
                }
            }

            Spacer(Modifier.height(Dimens.spacingMd))

            // Log Today Card
            Card(
                modifier  = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(Dimens.spacingMd)) {
                    Text("Registrar novo peso", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value         = weightInput,
                            onValueChange = { weightInput = it },
                            label         = { Text("Peso (kg)") },
                            singleLine    = true,
                            modifier      = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = {
                                LocalDate.now().let { today ->
                                    weightInput.toFloatOrNull()?.let { w ->
                                        scope.launch {
                                            prefs.addWeightEntry(today, w)
                                            weightInput = ""
                                        }
                                    }
                                }
                            },
                            enabled = weightInput.toFloatOrNull() != null
                        ) {
                            Text("Log")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // Entries List
        if (flatEntries.isEmpty()) {
            Text(
                text      = "Nenhuma entrada registrada.",
                style     = MaterialTheme.typography.bodyMedium,
                modifier  = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier       = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                itemsIndexed(flatEntries) { idx, (date, w) ->
                    val prev = flatEntries.getOrNull(idx - 1)?.second
                    val delta = prev?.let { w - it }
                    val deltaColor = when {
                        delta == null -> Color.Unspecified
                        delta < 0     -> Color(0xFF4CAF50)
                        else          -> Color(0xFFF44336)
                    }
                    val goalDiffColor = when {
                        diffGoal == null -> Color.Unspecified
                        diffGoal < 0     -> Color(0xFFF44336)
                        else             -> Color(0xFF4CAF50)
                    }

                    Card(
                        modifier  = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier             = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.spacingMd),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(date.format(DateTimeFormatter.ofPattern("dd/MM")), Modifier.weight(1f))
                            Text("%.1fkg".format(w), Modifier.weight(1f), textAlign = TextAlign.End)
                            Text(
                                delta?.let { "%+.1fkg".format(it) } ?: "--",
                                Modifier.weight(1f),
                                textAlign = TextAlign.End,
                                color     = deltaColor
                            )
                            Text(
                                diffGoal?.let { "%+.1fkg".format(it) } ?: "--",
                                Modifier.weight(1f),
                                textAlign = TextAlign.End,
                                color     = goalDiffColor
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // Exit link
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
