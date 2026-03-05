package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formfit.ui.theme.*

@Composable
fun DietScreen() {
    var selectedGoal by remember { mutableStateOf("Muscle Gain") }
    var selectedType by remember { mutableStateOf("Veg") }

    val milkSub = "Almond/Soy Milk"
    val curdSub = "Vegan Yogurt/Dairy-free Curd"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // Cleaner spacing
    ) {
        // Header
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Indian Diet Planner 🇮🇳",
                color = TextWhite,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Personalized for your Milk Allergy",
                color = AccentBlue,
                fontSize = 14.sp
            )
        }

        // Goal Selection
        GoalButtonRow(
            selectedGoal = selectedGoal,
            onGoalSelected = { selectedGoal = it }
        )

        // Meals
        val breakfast = buildBreakfast(selectedType, milkSub)
        val lunch = buildLunch(curdSub)
        val dinner = buildDinner(selectedGoal)

        MealCardDetailed("🌅 Breakfast", breakfast)
        MealCardDetailed("🍛 Lunch", lunch)
        MealCardDetailed("🍽 Dinner", dinner)
    }
}

// Extracted logic for cleaner composables
@Composable
private fun GoalButtonRow(
    selectedGoal: String,
    onGoalSelected: (String) -> Unit
) {
    Text(
        "Fitness Goal",
        color = TextWhite,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        GoalButton("Muscle Gain", selectedGoal == "Muscle Gain", Modifier.weight(1f)) {
            onGoalSelected(it)
        }
        GoalButton("Fat Loss", selectedGoal == "Fat Loss", Modifier.weight(1f)) {
            onGoalSelected(it)
        }
    }
}

@Composable
fun GoalButton(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .height(45.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) AccentBlue else CardDark)
            .clickable { onClick(text) }
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextWhite,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp // Added for consistency
        )
    }
}

@Composable
fun MealCardDetailed(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = CardDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                color = AccentBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                color = TextWhite.copy(alpha = 0.8f),
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
        }
    }
}

// Meal logic extracted for readability
private fun buildBreakfast(selectedType: String, milkSub: String): String =
    if (selectedType == "Veg") {
        """
        • Tofu Scramble / Moong Dal Chilla
        • Oats with $milkSub
        • Handful of Almonds
        Protein: 28g
        """.trimIndent()
    } else {
        """
        • 4 Egg Whites + 1 Whole Egg
        • Multigrain Roti
        • 1 Cup Coffee with $milkSub
        Protein: 32g
        """.trimIndent()
    }

private fun buildLunch(curdSub: String): String = """
    • Soya Chunk Curry / Dal Tadka
    • Brown Rice or 2 Roti
    • Big Bowl of Salad
    • $curdSub (Plant-based)
    Protein: 35g
""".trimIndent()

private fun buildDinner(selectedGoal: String): String =
    if (selectedGoal == "Fat Loss") {
        "• Roasted Paneer/Tofu (150g)\n• Stir Fry Broccoli & Capsicum\n• Clear Lentil Soup\nLow Carb | High Protein"
    } else {
        "• Soya/Tofu Curry\n• 2 Small Roti\n• Yellow Dal\nBalanced Muscle Meal"
    }

