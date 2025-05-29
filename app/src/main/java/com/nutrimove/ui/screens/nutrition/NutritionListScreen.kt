// app/src/main/java/com/nutrimove/ui/screens/nutrition/NutritionListScreen.kt
package com.nutrimove.ui.screens.nutrition

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import com.nutrimove.ui.theme.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NutritionListScreen(navController: NavController) {
    // 1) Start with empty meal plans
    val mealPlans = remember {
        mutableStateListOf(
            MealPlan("Ementa 1", emptyList()),
            MealPlan("Ementa 2", emptyList()),
            MealPlan("Ementa 3", emptyList())
        )
    }

    // 2) Listen for both updates (detail screens) and new plans
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collectLatest { entry ->
            // handle detail-screen edits ("plan_<idx>")
            val keys = entry.savedStateHandle.keys().filter { it.startsWith("plan_") }
            keys.forEach { key ->
                val idx = key.removePrefix("plan_").toIntOrNull()
                @Suppress("UNCHECKED_CAST")
                val updated = entry.savedStateHandle.get<List<FoodItem>>(key)
                if (idx != null && updated != null) {
                    mealPlans[idx] = mealPlans[idx].copy(items = updated)
                    entry.savedStateHandle.remove<List<FoodItem>>(key)
                }
            }

            // handle newly created plan ("new_plan")
            @Suppress("UNCHECKED_CAST")
            entry.savedStateHandle.get<List<FoodItem>>("new_plan")?.let { items ->
                val next = mealPlans.size + 1
                mealPlans.add(MealPlan("Ementa $next", items))
                entry.savedStateHandle.remove<List<FoodItem>>("new_plan")
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        Text(
            text      = "As suas ementas",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.spacingMd),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        LazyVerticalGrid(
            columns               = GridCells.Fixed(2),
            verticalArrangement   = Arrangement.spacedBy(Dimens.spacingMd),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMd),
            modifier              = Modifier.fillMaxSize()
        ) {
            // Existing meal plans
            itemsIndexed(mealPlans) { idx, plan ->
                MealPlanCard(plan.name, plan.items) {
                    navController.navigate("nutrition_detail/$idx")
                }
            }

            // “+” card to add a new plan
            item {
                Card(
                    modifier  = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { navController.navigate("add_plan") },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape     = MaterialTheme.shapes.medium
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar ementa")
                    }
                }
            }
        }
    }
}

@Composable
private fun MealPlanCard(
    title: String,
    items: List<FoodItem>,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape     = MaterialTheme.shapes.medium
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(Dimens.spacingMd),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (items.isEmpty()) {
                Text(
                    "Sem itens",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            } else {
                Text(
                    "${items.size} itens • ${items.sumOf { it.calories }} kcal",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}