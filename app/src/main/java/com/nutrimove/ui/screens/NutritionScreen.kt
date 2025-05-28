package com.nutrimove.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutrimove.ai.* // Assuming ChatRequest, Message, RetrofitClient, ChatResponse are here
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.nutrimove.BuildConfig // Correct import for BuildConfig

data class FoodItem(val name: String, val calories: Int)

val mealOptions = mapOf(
    "Pequeno-almoço" to listOf(
        FoodItem("100g Aveia com Leite", 415),
        FoodItem("200g Iogurte Grego com Morangos", 116),
        FoodItem("3 Ovos", 240),
        FoodItem("Torradas com Queijo/Fiambre/Manteiga", 220),
        FoodItem("Leite com cereais", 455),
        FoodItem("Frutas", 80)
    ),
    "Almoço" to listOf(
        FoodItem("Frango com Arroz ", 530),
        FoodItem("Arroz com Atum & Ovos", 553),
        FoodItem("Massa com carne Picada", 400)
    ),
    "Jantar" to listOf(
        FoodItem("Sopa de legumes", 150),
        FoodItem("Peixe com batata", 450),
        FoodItem("Ovos mexidos", 300)
    )
)

@Composable
fun NutritionScreen() {
    var totalCalories by remember { mutableStateOf(0) }
    val dailyLimit = 2200
    var aiPlan by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text("Planeador de Refeições", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                isLoading = true
                generateMealWithOpenAI { result ->
                    aiPlan = result
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gerar Plano com IA 🍽️")
        }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(10.dp))
        }

        aiPlan?.let {
            Text("📋 Sugestão da IA:", style = MaterialTheme.typography.titleMedium)
            Text(it) // Consider using a scrollable Text if the plan can be long
            Spacer(Modifier.height(20.dp))
        }

        Text("Calorias: $totalCalories / $dailyLimit kcal")
        LinearProgressIndicator(
            progress = (totalCalories.toFloat() / dailyLimit.toFloat()).coerceAtMost(1f), // Ensure float division
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        )

        mealOptions.forEach { (meal, options) ->
            MealDropdown(meal, options) { selectedFood ->
                if ((totalCalories + selectedFood.calories) <= dailyLimit) {
                    totalCalories += selectedFood.calories
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun MealDropdown(title: String, options: List<FoodItem>, onSelected: (FoodItem) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf<FoodItem?>(null) }

    Column {
        Text(title, style = MaterialTheme.typography.titleMedium)
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected?.name ?: "Escolher opção")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { foodItem ->
                DropdownMenuItem(
                    text = { Text("${foodItem.name} (${foodItem.calories} kcal)") },
                    onClick = {
                        selected = foodItem
                        expanded = false
                        onSelected(foodItem)
                    }
                )
            }
        }
    }
}

fun generateMealWithOpenAI(onResult: (String) -> Unit) {
    val prompt = "Cria um plano completo de refeições saudáveis para um dia inteiro, com pequeno-almoço, almoço, jantar e snacks, ideal para ganhar massa muscular."
    val request = ChatRequest(
        messages = listOf(Message("user", prompt))
    )

    // Use the BuildConfig field defined in your build.gradle
    val authHeader = "Bearer ${BuildConfig.OPENAI_API_KEY}"

    RetrofitClient.service.getMealPlan(authHeader, request) // Pass the authHeader
        .enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                val content = response.body()?.choices?.firstOrNull()?.message?.content
                onResult(content ?: "Nenhum resultado gerado.")
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                onResult("Erro ao gerar plano: ${t.localizedMessage}")
            }
        })
}