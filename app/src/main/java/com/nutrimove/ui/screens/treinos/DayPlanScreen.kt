package com.nutrimove.ui.screens.treinos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences

@Composable
fun DayPlanScreen(navController: NavController, dayIndex: Int) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)
    val plan by remember(days) { mutableStateOf(generateSplit(days)) }
    val day = plan.getOrNull(dayIndex) ?: return

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(day.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        day.exercises.forEachIndexed { idx, ex ->
            var checked by remember { mutableStateOf(false) }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                Spacer(Modifier.width(8.dp))
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("exerciseDetail/$dayIndex/$idx")
                        }
                ) {
                    Text(ex.name, style = MaterialTheme.typography.titleMedium)
                    Text("${ex.sets} séries x ${ex.reps} repetições",
                        style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(onClick = { /* AI hook */ }, Modifier.fillMaxWidth()) {
            Text("AI Pessoal\nPeça ajuda à nossa IA")
        }

        TextButton(onClick = { navController.popBackStack() },
            Modifier.align(Alignment.CenterHorizontally)) {
            Text("Sair")
        }
    }
}
