// app/src/main/java/com/nutrimove/ui/screens/profile/TipsScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.ui.theme.Dimens

// 1) Top-level data class for a section of tips
private data class Section(val title: String, val tips: List<String>)

@Composable
fun TipsScreen(navController: NavController) {
    // 2) Define your categorized tips
    val sections = listOf(
        Section(
            "Pré/Pós-Treino", listOf(
                "Alongue-se antes e depois dos exercícios para prevenir lesões.",
                "Aqueça o grupo muscular específico antes de iniciar a série.",
                "Faça pequenas pausas ativas se você trabalha sentado por muito tempo.",
                "Inclua exercícios de mobilidade para melhorar o alcance de movimento."
            )
        ),
        Section(
            "Hidratação & Recuperação", listOf(
                "Mantenha-se hidratado durante todo o dia, mesmo fora do treino.",
                "Durma 7–8 horas por noite para otimizar a recuperação.",
                "Tenha um lanche rico em proteínas logo após o treino.",
                "Gerencie o estresse com respiração ou meditação."
            )
        ),
        Section(
            "Nutrição", listOf(
                "Consuma proteínas magras em todas as refeições.",
                "Inclua vegetais coloridos para variedade de nutrientes.",
                "Ajuste suas calorias conforme seu objetivo (definição ou ganho).",
                "Não pule refeições; mantenha horários regulares."
            )
        ),
        Section(
            "Princípios de Treino", listOf(
                "Combine força com cardio para condicionamento completo.",
                "Varie exercícios a cada 4–6 semanas para evitar platôs.",
                "Controle o descanso entre séries para equilibrar força e resistência.",
                "Priorize a qualidade do movimento em vez da carga máxima.",
                "Aumente peso gradualmente para progredir com segurança.",
                "Registre séries e repetições para acompanhar sua evolução.",
                "Inclua exercícios compostos (agachamento, supino, terra)."
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // Header
        Text(
            text      = "Dicas de Fitness",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.spacingMd),
            textAlign = TextAlign.Center
        )

        // Expandable list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingSm)
        ) {
            items(sections) { section ->
                ExpandableSection(section)
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // Sair link
        Text(
            text      = "Sair",
            style     = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
            modifier  = Modifier
                .fillMaxWidth()
                .clickable { navController.popBackStack() }
                .padding(vertical = Dimens.spacingSm),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ExpandableSection(section: Section) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(300)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Section header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(Dimens.spacingMd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text  = section.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector   = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Recolher" else "Expandir"
                )
            }

            // Section content
            if (expanded) {
                Column(
                    modifier           = Modifier
                        .fillMaxWidth()
                        .padding(start = Dimens.spacingMd, end = Dimens.spacingMd, bottom = Dimens.spacingMd),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacingXs)
                ) {
                    section.tips.forEach { tip ->
                        Text(
                            text  = "• $tip",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
