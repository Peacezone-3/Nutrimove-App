// app/src/main/java/com/nutrimove/ui/screens/onboarding/NameEntryScreen.kt
package com.nutrimove.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NameEntryScreen(
    onNext: (String) -> Unit
) {
    // rememberSaveable ensures this survives recomposition/rotation
    var name by rememberSaveable { mutableStateOf("") }

    Column(
        modifier           = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text  = "Qual é o seu nome?",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value         = name,
            onValueChange = { newName -> name = newName }, // explicitly typed to pick the String overload
            label         = { Text("Insira seu nome") },
            singleLine    = true,
            modifier      = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick  = { onNext(name) },
            enabled  = name.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Continuar")
        }
    }
}
