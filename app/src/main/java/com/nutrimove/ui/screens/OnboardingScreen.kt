package com.nutrimove.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(navController: NavController) {
    var step by remember { mutableStateOf(0) }

    val name = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val height = remember { mutableStateOf("") }
    val activity = remember { mutableStateOf("") }
    val goal = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {

        Text("NutriMove: Comecemos!", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        when (step) {
            0 -> TextField(value = name.value, onValueChange = { name.value = it }, label = { Text("O teu nome") })
            1 -> TextField(value = age.value, onValueChange = { age.value = it }, label = { Text("Idade") })
            2 -> TextField(value = height.value, onValueChange = { height.value = it }, label = { Text("Altura (cm)") })
            3 -> TextField(value = weight.value, onValueChange = { weight.value = it }, label = { Text("Peso (kg)") })
            4 -> ActivityLevelDropdown(activity)
            5 -> GoalDropdown(goal)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (step < 5) step++ else navController.navigate("nutrition")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (step < 5) "Seguinte" else "Começar")
        }
    }
}

@Composable
fun ActivityLevelDropdown(selected: MutableState<String>) {
    val options = listOf("Sedentário", "Moderado", "Ativo", "Muito Ativo")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Nível de atividade:")
        Box {
            TextButton(onClick = { expanded = true }) {
                Text(selected.value.ifEmpty { "Escolher" })
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selected.value = label
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GoalDropdown(selected: MutableState<String>) {
    val options = listOf("Perder Peso", "Manter Peso", "Ganhar Massa")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Objetivo:")
        Box {
            TextButton(onClick = { expanded = true }) {
                Text(selected.value.ifEmpty { "Escolher" })
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selected.value = label
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
