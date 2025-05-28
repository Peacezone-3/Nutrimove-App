package com.nutrimove.ui.screens.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.nutrimove.R
import com.nutrimove.data.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    navController: NavController,
    userName: String
) {
    var step by remember { mutableStateOf(0) }
    val LAST_STEP = 5

    val age = remember { mutableStateOf("") }
    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val activity = remember { mutableStateOf("") }
    val goal = remember { mutableStateOf("") }
    val trainingDays = remember { mutableStateOf(3) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cycling))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Lottie Animation
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            // Greeting
            Text(
                text = "Olá, $userName!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            // Step Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Crossfade(targetState = step) { current ->
                        when (current) {
                            0 -> InputStep("Idade", age, Icons.Default.Cake)
                            1 -> InputStep("Altura (cm)", height, Icons.Default.Height)
                            2 -> InputStep("Peso (kg)", weight, Icons.Default.MonitorWeight)
                            3 -> DropdownStep(
                                "Nível de atividade",
                                activity,
                                listOf("Sedentário", "Moderado", "Ativo", "Muito Ativo")
                            )
                            4 -> DropdownStep(
                                "Objetivo",
                                goal,
                                listOf("Perder Peso", "Manter Peso", "Ganhar Massa")
                            )
                            5 -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Dias de treino por semana",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    var expanded by remember { mutableStateOf(false) }

                                    OutlinedButton(
                                        onClick = { expanded = true },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = "${trainingDays.value} dias",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        (2..6).forEach { day ->
                                            DropdownMenuItem(
                                                text = { Text("$day dias") },
                                                onClick = {
                                                    trainingDays.value = day
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (step < LAST_STEP) step++ else {
                                scope.launch {
                                    prefs.saveTrainingDays(trainingDays.value)
                                    prefs.setOnboardingCompleted()
                                }
                                navController.navigate("training") {
                                    popUpTo("onboarding_main/$userName") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (step < LAST_STEP) "Seguinte" else "Começar")
                    }
                }
            }
        }
    }
}

@Composable
fun InputStep(
    label: String,
    value: MutableState<String>,
    icon: ImageVector
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun DropdownStep(
    label: String,
    selected: MutableState<String>,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(4.dp))
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(selected.value.ifEmpty { "Escolher..." })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selected.value = item
                        expanded = false
                    }
                )
            }
        }
    }
}
