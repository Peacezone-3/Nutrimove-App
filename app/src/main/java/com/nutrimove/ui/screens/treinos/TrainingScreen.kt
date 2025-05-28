package com.nutrimove.ui.screens.treinos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences

@Composable
fun TrainingScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)
    val plan by remember(days) { mutableStateOf(generateSplit(days)) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Plano de treino", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        plan.chunked(2).forEach { row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { day ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                val idx = plan.indexOf(day)
                                navController.navigate("dayPlan/$idx")
                            },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                day.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(8.dp))
                            day.exercises.forEach { ex ->
                                Text(
                                    "- ${ex.name}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
