package com.nutrimove.ui.screens.nutrition

/** A single food item with its calorie count. */
data class FoodItem(
    val name: String,
    val calories: Int
)

data class MealPlan(
    val name: String,
    val items: List<FoodItem>
)

/** Your meal categories and options. */
val mealOptions: Map<String, List<FoodItem>> = mapOf(
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
