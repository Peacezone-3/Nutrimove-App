package com.nutrimove.ui.screens.treinos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences

@Composable
fun ExerciseDetailScreen(
    navController: NavController,
    dayIndex: Int,
    exIndex: Int
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)
    val plan by remember(days) { mutableStateOf(generateSplit(days)) }
    val ex = plan.getOrNull(dayIndex)?.exercises?.getOrNull(exIndex) ?: return

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(ex.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.height(16.dp))

        Text("Músculo alvo: ${ex.targetMuscle}", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))

        Text("Dicas:", style = MaterialTheme.typography.titleMedium)
        ex.tips.forEach { tip ->
            Text("• $tip", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.weight(1f))

        TextButton(onClick = { navController.popBackStack() },
            Modifier.align(Alignment.CenterHorizontally)) {
            Text("Sair")
        }
    }
}
