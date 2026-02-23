package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun DietScreen() {

    var selectedGoal by remember { mutableStateOf("Muscle Gain") }
    var selectedType by remember { mutableStateOf("Non-Veg") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1423))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            "Indian Diet Planner 🇮🇳",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 Goal Selection
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { selectedGoal = "Muscle Gain" }) {
                Text("Muscle Gain")
            }
            Button(onClick = { selectedGoal = "Fat Loss" }) {
                Text("Fat Loss")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔥 Diet Type Selection
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { selectedType = "Veg" }) {
                Text("Veg")
            }
            Button(onClick = { selectedType = "Non-Veg" }) {
                Text("Non-Veg")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Goal: $selectedGoal | Type: $selectedType",
            color = Color.Green,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        val breakfast = if (selectedType == "Veg") {
            """
• Paneer Bhurji / Tofu Scramble
• Moong Dal Chilla
• Oats with Milk
Protein: 30g
            """.trimIndent()
        } else {
            """
• 4 Egg Whites + 2 Whole Eggs
• Oats / Multigrain Roti
• 1 Glass Milk
Protein: 35g
            """.trimIndent()
        }

        val lunch = if (selectedType == "Veg") {
            """
• Rajma / Chole / Soya Chunk Curry
• Brown Rice
• 2 Roti
• Salad
Protein: 35g
            """.trimIndent()
        } else {
            """
• 150g Chicken Breast / Fish
• Brown Rice
• 2 Roti
• Sabzi + Curd
Protein: 40g
            """.trimIndent()
        }

        val dinner = if (selectedGoal == "Fat Loss") {
            """
• Paneer / Chicken (150g)
• Stir Fry Vegetables
• Soup
Low Carb | High Protein
            """.trimIndent()
        } else {
            """
• Paneer / Chicken (150g)
• 1-2 Roti
• Dal + Sabzi
Balanced Muscle Meal
            """.trimIndent()
        }

        MealCard("🌅 Breakfast", breakfast)
        Spacer(modifier = Modifier.height(16.dp))

        MealCard(
            "🥤 Mid Meal",
            """
• Banana + Peanut Butter
• OR Sprouts Chaat
• OR Protein Shake
Protein: 15-20g
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        MealCard("🍛 Lunch", lunch)
        Spacer(modifier = Modifier.height(16.dp))

        MealCard(
            "☕ Evening Snack",
            """
• Roasted Chana
• Greek Yogurt
• Black Coffee
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        MealCard("🍽 Dinner", dinner)

        Spacer(modifier = Modifier.height(30.dp))
    }
}