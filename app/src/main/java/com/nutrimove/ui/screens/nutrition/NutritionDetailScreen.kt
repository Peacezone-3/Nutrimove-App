// app/src/main/java/com/nutrimove/ui/screens/nutrition/NutritionDetailScreen.kt
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
import com.nutrimove.util.orZero


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDetailScreen(
    navController: NavController,
    planIndex: Int
) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val userMap by prefs.userFlow.collectAsState(initial = emptyMap())

    // Stub: an existing plan broken into 6 options per meal
    val planMeals = mapOf(
        "Pequeno-Almoço" to listOf(
            FoodItem("Omelete (2 ovos médios)", 200),
            FoodItem("Aveia com leite magro (40 g + 200 ml)", 180),
            FoodItem("Torrada integral com manteiga (2 fatias + 10 g)", 150),
            FoodItem("Iogurte grego com mel (150 g + 1 c. chá)", 170),
            FoodItem("Banana média (~120 g)", 105),
            FoodItem("Pão integral com queijo fresco (1 fatia + 30 g)", 200)
        ),
        "Almoço" to listOf(
            FoodItem("Peito de Frango grelhado (150 g)", 250),
            FoodItem("Arroz Integral (100 g)", 200),
            FoodItem("Salada mista (alface, tomate, pepino)", 100),
            FoodItem("Salmão ao forno (120 g)", 280),
            FoodItem("Quinoa cozida (100 g)", 120),
            FoodItem("Wrap de peru e vegetais", 230)
        ),
        "Lanche" to listOf(
            FoodItem("Iogurte natural (125 g)", 100),
            FoodItem("Nozes (30 g)", 180),
            FoodItem("Barra de cereal", 150),
            FoodItem("Maçã média (~150 g)", 95),
            FoodItem("Smoothie de frutas (250 ml)", 160),
            FoodItem("Torrada de abacate (1 fatia + 50 g)", 190)
        ),
        "Jantar" to listOf(
            FoodItem("Peixe Assado (150 g)", 220),
            FoodItem("Legumes Cozidos (150 g)", 100),
            FoodItem("Carne magra grelhada (150 g)", 260),
            FoodItem("Sopa de legumes (300 ml)", 120),
            FoodItem("Pasta integral com tomate (100 g)", 210),
            FoodItem("Tofu salteado com vegetais (150 g)", 180)
        )
    )
    val categoryList = listOf("Pequeno-Almoço", "Almoço", "Lanche", "Jantar")

    // Compute TDEE
    val age      = (userMap[UserKeys.AGE]      as? Int).orZero()
    val height   = (userMap[UserKeys.HEIGHT]   as? Int).orZero()
    val weight   = (userMap[UserKeys.WEIGHT]   as? Int).orZero()
    val activity = (userMap[UserKeys.ACTIVITY] as? String).orEmpty()

    val factor: Double = when (activity) {
        "Sedentário"  -> 1.2
        "Moderado"    -> 1.375
        "Ativo"       -> 1.55
        "Muito Ativo" -> 1.725
        else          -> 1.2
    }
    val bmr  = 10.0 * weight + 6.25 * height - 5.0 * age + 5.0
    val tdee = (bmr * factor).toInt()

    // State: start with zero calories, no selections
    var totalCalories by remember { mutableStateOf(0) }
    var openCategory   by remember { mutableStateOf<String?>(null) }
    // each category begins empty
    val selectedMap = remember {
        planMeals.keys.associateWith { mutableStateListOf<FoodItem>() }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // ── Title ──
        Text(
            text      = "Ementa",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Progress Bar ──
        Box(
            Modifier
                .fillMaxWidth(0.8f)
                .height(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                .align(Alignment.CenterHorizontally)
        ) {
            LinearProgressIndicator(
                progress = { totalCalories / tdee.toFloat() },
                modifier = Modifier.matchParentSize(),
                color    = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.height(Dimens.spacingMd))

        // ── Centered Meal Buttons & Item Lists ──
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
                categoryList.forEach { category ->
                    val isOpen = openCategory == category
                    Button(
                        onClick  = { openCategory = if (isOpen) null else category },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.spacingXs),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFADD8E6),
                            contentColor   = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape    = MaterialTheme.shapes.medium
                    ) {
                        Text(category, style = MaterialTheme.typography.bodyLarge)
                    }
                    if (isOpen) {
                        planMeals[category]?.forEach { item ->
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
                                        else Color.Transparent
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
                                    onClick  = null
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

        // ── Save & Exit Button ──
        TextButton(
            onClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("plan_$planIndex", selectedMap.values.flatten())
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sair")
        }
    }
}
