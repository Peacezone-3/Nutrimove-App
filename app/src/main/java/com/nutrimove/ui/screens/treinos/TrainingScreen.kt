// app/src/main/java/com/nutrimove/ui/screens/treinos/TrainingScreen.kt
package com.nutrimove.ui.screens.treinos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens
import com.nutrimove.ui.screens.treinos.generateSplit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrainingScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)

    // Regenerate split when `days` changes
    val plan by remember(days) { mutableStateOf(generateSplit(days)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // Title
        Text(
            text      = "Plano de Treino",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.spacingMd),
            textAlign = TextAlign.Center
        )

        // Scrollable 2‐column grid of square cards
        LazyVerticalGrid(
            columns               = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMd),
            verticalArrangement   = Arrangement.spacedBy(Dimens.spacingMd),
            modifier              = Modifier.fillMaxSize()
        ) {
            items(plan) { day ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)    // square cards
                        .clickable {
                            val idx = plan.indexOf(day)
                            navController.navigate("day_plan/$idx")
                        },
                    shape     = RoundedCornerShape(Dimens.cornerDefault),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier         = Modifier
                            .fillMaxSize()
                            .padding(Dimens.spacingMd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text      = day.title,
                            style     = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            maxLines  = 1,
                            overflow  = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
