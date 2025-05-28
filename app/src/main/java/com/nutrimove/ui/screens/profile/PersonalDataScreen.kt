package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.data.UserKeys
import kotlinx.coroutines.launch

@Composable
fun ProfilePersonalScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val userData by prefs.userFlow.collectAsState(initial = emptyMap())
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var activity by remember { mutableStateOf("") }

    // Atualiza os campos sempre que os dados do usuário mudam
    LaunchedEffect(userData) {
        name = (userData[UserKeys.NAME] as? String).orEmpty()
        age = (userData[UserKeys.AGE] as? Int)?.toString().orEmpty()
        weight = (userData[UserKeys.WEIGHT] as? Int)?.toString().orEmpty()
        height = (userData[UserKeys.HEIGHT] as? Int)?.toString().orEmpty()
        goal = (userData[UserKeys.GOAL] as? String).orEmpty()
        activity = (userData[UserKeys.ACTIVITY] as? String).orEmpty()
    }

    val goalOptions = listOf("Perder Peso", "Manter Peso", "Ganhar Massa")
    val activityOptions = listOf("Sedentário", "Moderado", "Ativo", "Muito Ativo")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Dados Pessoais", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Idade") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Altura (cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        DropdownField("Objetivo", goal, goalOptions) { goal = it }
        Spacer(modifier = Modifier.height(12.dp))

        DropdownField("Nível de atividade", activity, activityOptions) { activity = it }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    prefs.saveUserData(
                        name = name,
                        age = age.toIntOrNull() ?: 0,
                        goal = goal,
                        height = height.toIntOrNull() ?: 0,
                        weight = weight.toIntOrNull() ?: 0,
                        activity = activity
                    )
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedValue.ifBlank { "Selecionar" })
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
