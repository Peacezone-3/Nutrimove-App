// app/src/main/java/com/nutrimove/ui/screens/nutrition/AddMealPlanScreen.kt
package com.nutrimove.ui.screens.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserKeys
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealPlanScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val userMap by prefs.userFlow.collectAsState(initial = emptyMap())

    // Compute TDEE
    val age      = (userMap[UserKeys.AGE]      as? Int) ?: 0
    val height   = (userMap[UserKeys.HEIGHT]   as? Int) ?: 0
    val weight   = (userMap[UserKeys.WEIGHT]   as? Int) ?: 0
    val activity = (userMap[UserKeys.ACTIVITY] as? String) ?: ""
    val factor   = when (activity) {
        "Sedentário"  -> 1.2
        "Moderado"    -> 1.375
        "Ativo"       -> 1.55
        "Muito Ativo" -> 1.725
        else          -> 1.2
    }
    val bmr  = 10.0 * weight + 6.25 * height - 5.0 * age + 5.0
    val tdee = (bmr * factor).toInt()

    // State
    var totalCalories by remember { mutableStateOf(0) }
    var openCategory  by remember { mutableStateOf<String?>(null) }
    val selectedMap = remember {
        mealOptions.keys.associateWith { mutableStateListOf<FoodItem>() }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // ── Title ──
        Text(
            text      = "Criar Novo Plano",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Clean Progress Bar ──
        Box(
            Modifier
                .fillMaxWidth(0.8f)
                .height(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                .align(Alignment.CenterHorizontally)
        ) {
            LinearProgressIndicator(
                progress = { (totalCalories / tdee.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier.matchParentSize(),
                color    = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Centered Categories & Dropdowns ──
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier            = Modifier.fillMaxWidth(0.8f)
            ) {
                mealOptions.forEach { (category, options) ->
                    val isOpen = openCategory == category

                    // Always-filled light-blue pill
                    Button(
                        onClick  = { openCategory = if (isOpen) null else category },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.spacingXs),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFADD8E6),
                            contentColor   = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape    = MaterialTheme.shapes.medium
                    ) {
                        Text(category, style = MaterialTheme.typography.bodyLarge)
                    }

                    if (isOpen) {
                        options.forEach { item ->
                            val selList = selectedMap[category]!!
                            val checked = item in selList
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (checked)
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        else
                                            Color.Transparent
                                    )
                                    .clickable {
                                        if (checked) {
                                            selList.remove(item)
                                            totalCalories -= item.calories
                                        } else if (totalCalories + item.calories <= tdee) {
                                            selList.add(item)
                                            totalCalories += item.calories
                                        }
                                    }
                                    .padding(Dimens.spacingSm)
                            ) {
                                RadioButton(
                                    selected = checked,
                                    onClick  = null,
                                    colors   = RadioButtonDefaults.colors(
                                        selectedColor   = Color.Black,
                                        unselectedColor = Color.Gray
                                    )
                                )
                                Spacer(Modifier.width(Dimens.spacingSm))
                                Text(item.name, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                        Spacer(Modifier.height(Dimens.spacingMd))
                    }
                }
            }
        }

        // ── Fixed Action Buttons ──
        Button(
            onClick   = {
                // Gather all selected items
                val newItems = selectedMap.values.flatten()
                // Pass them back as "new_plan"
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("new_plan", newItems)
                // Return
                navController.popBackStack()
            },
            modifier  = Modifier.fillMaxWidth(),
            shape     = MaterialTheme.shapes.medium
        ) {
            Text("Gerar Plano")
        }
        Spacer(Modifier.height(Dimens.spacingSm))
        Button(
            onClick   = { /* AI helper */ },
            modifier  = Modifier.fillMaxWidth(),
            shape     = MaterialTheme.shapes.medium
        ) {
            Text("AI Helper")
        }
    }
}
