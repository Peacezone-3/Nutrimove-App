package com.nutrimove.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val trainingPlan = mapOf(
    "Segunda-feira" to listOf("Supino reto", "Tríceps pulley", "Peck deck"),
    "Terça-feira" to listOf("Agachamento", "Leg press", "Flexão plantar"),
    "Quarta-feira" to listOf("Cardio 30min", "Abdominais", "Prancha"),
    "Quinta-feira" to listOf("Puxada frente", "Remada baixa", "Bíceps rosca"),
    "Sexta-feira" to listOf("Desenvolvimento", "Elevação lateral", "Arnold press")
)

@Composable
fun TrainingScreen() {
    var selectedDay by remember { mutableStateOf<String?>(null) }

    Column(Modifier.padding(16.dp)) {
        Text("Plano Semanal de Treino", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(10.dp))

        trainingPlan.keys.forEach { day ->
            Text(
                text = day,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedDay = day }
                    .padding(vertical = 6.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(16.dp))
        selectedDay?.let {
            Text("Treino de $it:", style = MaterialTheme.typography.titleLarge)
            trainingPlan[it]?.forEach { ex -> Text("- $ex") }
        }
    }
}
