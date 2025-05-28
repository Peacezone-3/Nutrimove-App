package com.nutrimove.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(Modifier.padding(16.dp)) {
        Text("Perfil", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(10.dp))

        Text("Nome: João Silva")
        Text("Peso atual: 74 kg")
        Text("Altura: 176 cm")
        Text("Objetivo: Perder peso")
        Text("Nível de atividade: Moderado")

        Spacer(Modifier.height(24.dp))
        Text("📈 Gráficos de progresso (em breve)")
        Text("📊 Calorias por dia / Peso ao longo do tempo")
    }
}
