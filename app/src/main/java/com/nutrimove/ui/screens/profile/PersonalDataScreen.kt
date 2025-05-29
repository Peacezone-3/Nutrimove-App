// app/src/main/java/com/nutrimove/ui/screens/profile/PersonalDataScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.nutrimove.data.UserKeys
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment


@Composable
fun PersonalDataScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val userMap by prefs.userFlow.collectAsState(initial = emptyMap())
    val scope   = rememberCoroutineScope()

    var name      by remember { mutableStateOf((userMap[UserKeys.NAME]     as? String).orEmpty()) }
    var ageStr    by remember { mutableStateOf((userMap[UserKeys.AGE]      as? Int)?.toString().orEmpty()) }
    var heightStr by remember { mutableStateOf((userMap[UserKeys.HEIGHT]  as? Int)?.toString().orEmpty()) }
    var weightStr by remember { mutableStateOf((userMap[UserKeys.WEIGHT]  as? Int)?.toString().orEmpty()) }
    var activity  by remember { mutableStateOf((userMap[UserKeys.ACTIVITY] as? String).orEmpty()) }
    var goal      by remember { mutableStateOf((userMap[UserKeys.GOAL]     as? String).orEmpty()) }

    Column(
        modifier           = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text  = "Dados pessoais",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(Dimens.spacingMd))

        // Nome
        OutlinedTextField(
            value        = name,
            onValueChange= { name = it },
            label        = { Text("Nome") },
            singleLine   = true,
            modifier     = Modifier.fillMaxWidth(),
            shape        = MaterialTheme.shapes.small
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSm))

        // Idade
        OutlinedTextField(
            value        = ageStr,
            onValueChange= { ageStr = it },
            label        = { Text("Idade") },
            singleLine   = true,
            modifier     = Modifier.fillMaxWidth(),
            shape        = MaterialTheme.shapes.small
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSm))

        // Altura
        OutlinedTextField(
            value        = heightStr,
            onValueChange= { heightStr = it },
            label        = { Text("Altura (cm)") },
            singleLine   = true,
            modifier     = Modifier.fillMaxWidth(),
            shape        = MaterialTheme.shapes.small
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSm))

        // Peso
        OutlinedTextField(
            value        = weightStr,
            onValueChange= { weightStr = it },
            label        = { Text("Peso (kg)") },
            singleLine   = true,
            modifier     = Modifier.fillMaxWidth(),
            shape        = MaterialTheme.shapes.small
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSm))

        // Nível de atividade
        DropdownMenuField(
            label    = "Nível de atividade",
            options  = listOf("Sedentário","Moderado","Ativo","Muito Ativo"),
            selected = activity
        ) {
            activity = it
        }
        Spacer(modifier = Modifier.height(Dimens.spacingSm))

        // Objetivo
        DropdownMenuField(
            label    = "Objetivo",
            options  = listOf("Perder Peso","Manter Peso","Ganhar Massa"),
            selected = goal
        ) {
            goal = it
        }
        Spacer(modifier = Modifier.height(Dimens.spacingLg))

        Button(
            onClick = {
                scope.launch {
                    prefs.saveUserData(
                        name     = name,
                        age      = ageStr.toIntOrNull() ?: 0,
                        goal     = goal,
                        height   = heightStr.toIntOrNull() ?: 0,
                        weight   = weightStr.toIntOrNull() ?: 0,
                        activity = activity
                    )
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            shape    = MaterialTheme.shapes.medium
        ) {
            Text("Salvar alterações", style = MaterialTheme.typography.labelSmall)
        }
    }
}

// Helper defined below or in the same file
@Composable
private fun DropdownMenuField(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(Dimens.spacingXs))
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            shape    = MaterialTheme.shapes.small
        ) {
            Text(selected.ifEmpty { "Escolher…" }, style = MaterialTheme.typography.bodyMedium)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text    = { Text(opt) },
                    onClick = {
                        onSelect(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}
