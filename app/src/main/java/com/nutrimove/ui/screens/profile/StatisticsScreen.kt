// app/src/main/java/com/nutrimove/ui/screens/profile/StatisticsScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import java.time.temporal.ChronoUnit

@Composable
fun StatisticsScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }

    // Data
    val trainingDays by prefs.trainingDaysFlow.collectAsState(initial = 3)
    val goalFlow     by prefs.goalWeightFlow.collectAsState(initial = null)
    val entriesMap   by prefs.weightEntriesFlow.collectAsState(initial = emptyMap())
    val goalWeight   = goalFlow

    // Flatten
    val flatWeights = remember(entriesMap) {
        entriesMap.toList()
            .sortedBy { it.first }
            .flatMap { it.second }
    }

    // Metrics
    val dates = entriesMap.keys.sorted()
    val daysTracked = if (dates.size > 1)
        ChronoUnit.DAYS.between(dates.first(), dates.last()).toInt() + 1
    else dates.size
    val totalEntries  = flatWeights.size
    val firstWeight   = flatWeights.firstOrNull()
    val lastWeight    = flatWeights.lastOrNull()
    val changeTotal   = if (firstWeight != null && lastWeight != null) lastWeight - firstWeight else null
    val avgDailyChange= changeTotal?.let { it / daysTracked }
    val averageWeight = flatWeights.takeIf { it.isNotEmpty() }?.average()?.toFloat()
    val goalDiff      = if (goalWeight != null && lastWeight != null) goalWeight - lastWeight else null

    // Layout constants
    val summaryCardHeight = 100.dp
    val detailCardHeight  = 80.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // ── Header ──
        Text(
            text      = "As suas Estatísticas",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Summary Row ──
        Row(
            modifier             = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSm)
        ) {
            @Composable
            fun SummaryCard(label: String, value: String, color: Color) {
                Card(
                    modifier  = Modifier
                        .weight(1f)
                        .height(summaryCardHeight),
                    colors    = CardDefaults.cardColors(containerColor = color),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier           = Modifier.fillMaxSize(),
                        contentAlignment   = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(label, style = MaterialTheme.typography.bodySmall, color = Color.White)
                            Spacer(Modifier.height(4.dp))
                            Text(value, style = MaterialTheme.typography.titleMedium, color = Color.White)
                        }
                    }
                }
            }

            SummaryCard("Treino/semana", "$trainingDays dias", MaterialTheme.colorScheme.primary)
            SummaryCard("Peso inicial", firstWeight?.let { "%.1f kg".format(it) } ?: "--", MaterialTheme.colorScheme.secondary)
            SummaryCard("Peso atual", lastWeight?.let { "%.1f kg".format(it) } ?: "--", MaterialTheme.colorScheme.tertiary)
            SummaryCard("Δ Total", changeTotal?.let { "%+.1f kg".format(it) } ?: "--", MaterialTheme.colorScheme.primaryContainer)
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Detailed Grid ──
        val detailedStats = listOf(
            "Entradas"        to "$totalEntries",
            "Dias registro"   to "$daysTracked",
            "Δ média diária"  to (avgDailyChange?.let { "%.2f kg/dia".format(it) } ?: "--"),
            "Peso médio"      to (averageWeight?.let { "%.1f kg".format(it) } ?: "--"),
            "Até a meta"      to (goalDiff?.let { "%+.1f kg".format(it) } ?: "--")
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingSm)
        ) {
            itemsIndexed(detailedStats.chunked(2)) { _, pairList ->
                Row(
                    modifier             = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSm)
                ) {
                    pairList.forEach { (label, value) ->
                        Card(
                            modifier  = Modifier
                                .weight(1f)
                                .height(detailCardHeight),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier             = Modifier
                                    .fillMaxSize()
                                    .padding(Dimens.spacingMd),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.CenterVertically
                            ) {
                                Text(label, style = MaterialTheme.typography.bodyMedium)
                                Text(value, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.End)
                            }
                        }
                    }
                    if (pairList.size == 1) {
                        Spacer(modifier = Modifier
                            .weight(1f)
                            .height(detailCardHeight)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Exit Link ──
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
