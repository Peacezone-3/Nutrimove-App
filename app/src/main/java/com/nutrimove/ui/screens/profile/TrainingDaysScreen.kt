package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@Composable
fun TrainingDaysScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val currentDays by prefs.trainingDaysFlow.collectAsState(initial = 3)
    val selectedDays = remember { mutableStateOf(currentDays) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dias de treino por semana",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("${selectedDays.value} dias")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (2..6).forEach { d ->
                DropdownMenuItem(
                    text = { Text("$d dias") },
                    onClick = {
                        selectedDays.value = d
                        expanded = false
                    }
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                scope.launch {
                    prefs.saveTrainingDays(selectedDays.value)
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Salvar")
        }
    }
}
