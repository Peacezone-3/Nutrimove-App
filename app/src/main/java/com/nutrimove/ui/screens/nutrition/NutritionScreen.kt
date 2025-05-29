package com.nutrimove.ui.screens.nutrition

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserKeys
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.components.AppCard
import com.nutrimove.ui.theme.Dimens
import com.nutrimove.ui.theme.NutriMoveTheme
import com.nutrimove.util.orZero

// ← Add these two:
import com.nutrimove.ui.screens.nutrition.mealOptions
import com.nutrimove.ui.screens.nutrition.FoodItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NutritionScreen(navController: NavController) {
    NutriMoveTheme {
        val context = LocalContext.current
        val prefs   = remember { UserPreferences(context) }
        val userMap by prefs.userFlow.collectAsState(initial = emptyMap())

        // Compute TDEE
        val age      = (userMap[UserKeys.AGE]      as? Int).orZero()
        val height   = (userMap[UserKeys.HEIGHT]   as? Int).orZero()
        val weight   = (userMap[UserKeys.WEIGHT]   as? Int).orZero()
        val activity = (userMap[UserKeys.ACTIVITY] as? String).orEmpty()

        val factor = when (activity) {
            "Sedentário"  -> 1.2
            "Moderado"    -> 1.375
            "Ativo"       -> 1.55
            "Muito Ativo" -> 1.725
            else          -> 1.2
        }
        val bmr  = 10.0 * weight + 6.25 * height - 5.0 * age + 5.0
        val tdee = (bmr * factor).toInt()

        // Local UI state
        var totalCalories by remember { mutableStateOf(0) }
        val expandedMap = remember {
            mealOptions.keys.associateWith { mutableStateOf(false) }
        }
        val selectedMap = remember {
            mealOptions.keys.associateWith { mutableStateListOf<FoodItem>() }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.spacingMd)
        ) {
            // ── Header ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.spacingMd),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Planeador de Refeições",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(Modifier.height(Dimens.spacingSm))
                LinearProgressIndicator(
                    progress   = { (totalCalories / tdee.toFloat()).coerceIn(0f, 1f) },
                    modifier   = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(Dimens.spacingSm))
                Text(
                    "$totalCalories / $tdee kcal",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // ── Grid of meal-categories + “Add plan” card ──
            LazyVerticalGrid(
                columns               = GridCells.Adaptive(minSize = Dimens.cardWidth),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMd),
                verticalArrangement   = Arrangement.spacedBy(Dimens.spacingMd),
                contentPadding        = PaddingValues(Dimens.spacingMd),
                modifier              = Modifier.fillMaxSize()
            ) {
                items(mealOptions.entries.toList(), key = { it.key }) { (meal, options) ->
                    AppCard(
                        title    = meal,
                        subtitle = "${selectedMap[meal]?.size ?: 0}/${options.size}"
                    ) {
                        expandedMap[meal]?.value = !(expandedMap[meal]?.value ?: false)
                    }

                    if (expandedMap[meal]?.value == true) {
                        options.forEach { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = Dimens.spacingMd,
                                        top   = Dimens.spacingSm
                                    )
                            ) {
                                val selectedList = selectedMap[meal]!!
                                val isChecked = item in selectedList
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        if (checked && totalCalories + item.calories <= tdee) {
                                            totalCalories += item.calories
                                            selectedList.add(item)
                                        } else if (!checked) {
                                            totalCalories -= item.calories
                                            selectedList.remove(item)
                                        }
                                    }
                                )
                                Spacer(Modifier.width(Dimens.spacingSm))
                                Text(
                                    "${item.name} (${item.calories} kcal)",
                                    style    = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                // “Add Plan” card
                item {
                    Card(
                        modifier = Modifier
                            .size(Dimens.cardWidth, Dimens.cardHeight)
                            .clickable { navController.navigate("add_plan") },
                        shape     = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Adicionar plano"
                            )
                        }
                    }
                }
            }
        }
    }
}
