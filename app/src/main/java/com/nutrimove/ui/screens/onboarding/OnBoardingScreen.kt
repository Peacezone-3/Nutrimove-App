// app/src/main/java/com/nutrimove/ui/screens/onboarding/OnBoardingScreen.kt
package com.nutrimove.ui.screens.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.nutrimove.R
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    userName: String
) {
    var step by rememberSaveable { mutableStateOf(0) }
    val LAST_STEP = 5

    // form state
    var age        by rememberSaveable { mutableStateOf("") }
    var height     by rememberSaveable { mutableStateOf("") }
    var weight     by rememberSaveable { mutableStateOf("") }
    var activity   by rememberSaveable { mutableStateOf("") }
    var goal       by rememberSaveable { mutableStateOf("") }
    var daysCount  by rememberSaveable { mutableStateOf(3) }

    val focusManager = LocalFocusManager.current
    val keyboardCtrl = LocalSoftwareKeyboardController.current

    // Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cycling))
    val progress    by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    // prefs
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val scope   = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color    = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier           = Modifier
                .fillMaxSize()
                .padding(Dimens.spacingLg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                progress    = { progress },
                modifier    = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(Modifier.height(Dimens.spacingMd))

            Text(
                text  = "Olá, $userName!",
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(Modifier.height(Dimens.spacingMd))

            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(Dimens.spacingMd)) {
                    Crossfade(targetState = step) { current ->
                        when (current) {
                            0 -> OutlinedTextField(
                                value = age,
                                onValueChange = { age = it },
                                label = { Text("Idade") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction    = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            1 -> OutlinedTextField(
                                value = height,
                                onValueChange = { height = it },
                                label = { Text("Altura (cm)") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction    = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            2 -> OutlinedTextField(
                                value = weight,
                                onValueChange = { weight = it },
                                label = { Text("Peso (kg)") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction    = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            3 -> {
                                Text(
                                    text  = "Nível de atividade",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(Dimens.spacingSm))

                                var expandedAct by rememberSaveable { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded         = expandedAct,
                                    onExpandedChange = { expandedAct = !expandedAct }
                                ) {
                                    TextField(
                                        value         = activity,
                                        onValueChange = {},
                                        readOnly      = true,
                                        label         = { Text("Selecione") },
                                        trailingIcon  = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expandedAct)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded         = expandedAct,
                                        onDismissRequest = { expandedAct = false }
                                    ) {
                                        listOf("Sedentário", "Moderado", "Ativo", "Muito Ativo")
                                            .forEach { option ->
                                                DropdownMenuItem(
                                                    text    = { Text(option) },
                                                    onClick = {
                                                        activity   = option
                                                        expandedAct = false
                                                    }
                                                )
                                            }
                                    }
                                }
                            }
                            4 -> {
                                Text(
                                    text  = "Objetivo",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(Dimens.spacingSm))

                                var expandedGoal by rememberSaveable { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded         = expandedGoal,
                                    onExpandedChange = { expandedGoal = !expandedGoal }
                                ) {
                                    TextField(
                                        value         = goal,
                                        onValueChange = {},
                                        readOnly      = true,
                                        label         = { Text("Selecione") },
                                        trailingIcon  = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expandedGoal)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded         = expandedGoal,
                                        onDismissRequest = { expandedGoal = false }
                                    ) {
                                        listOf("Perder Peso", "Manter Peso", "Ganhar Massa")
                                            .forEach { option ->
                                                DropdownMenuItem(
                                                    text    = { Text(option) },
                                                    onClick = {
                                                        goal        = option
                                                        expandedGoal = false
                                                    }
                                                )
                                            }
                                    }
                                }
                            }
                            5 -> {
                                Text(
                                    text  = "Dias de treino por semana",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(Dimens.spacingSm))

                                var expandedDays by rememberSaveable { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded         = expandedDays,
                                    onExpandedChange = { expandedDays = !expandedDays }
                                ) {
                                    TextField(
                                        value         = "$daysCount dias",
                                        onValueChange = {},
                                        readOnly      = true,
                                        label         = { Text("Selecione") },
                                        trailingIcon  = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expandedDays)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded         = expandedDays,
                                        onDismissRequest = { expandedDays = false }
                                    ) {
                                        (2..6).forEach { d ->
                                            DropdownMenuItem(
                                                text    = { Text("$d dias") },
                                                onClick = {
                                                    daysCount    = d
                                                    expandedDays = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(Dimens.spacingLg))

                    Button(
                        onClick = {
                            if (step < LAST_STEP) {
                                step++
                                keyboardCtrl?.hide()
                            } else {
                                scope.launch {
                                    prefs.saveUserData(
                                        name     = userName,
                                        age      = age.toIntOrNull() ?: 0,
                                        goal     = goal,
                                        height   = height.toIntOrNull() ?: 0,
                                        weight   = weight.toIntOrNull() ?: 0,
                                        activity = activity
                                    )
                                    prefs.saveTrainingDays(daysCount)
                                    prefs.setOnboardingCompleted()
                                }
                                navController.navigate("training") {
                                    popUpTo("onboarding_name") { inclusive = true }
                                }
                            }
                        },
                        enabled = when (step) {
                            0 -> age.isNotBlank()
                            1 -> height.isNotBlank()
                            2 -> weight.isNotBlank()
                            3 -> activity.isNotBlank()
                            4 -> goal.isNotBlank()
                            5 -> true
                            else -> false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape    = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text  = if (step < LAST_STEP) "Seguinte" else "Começar",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}