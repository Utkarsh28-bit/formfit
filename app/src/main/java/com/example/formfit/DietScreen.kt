package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.formfit.ui.theme.*

@Composable
fun DietScreen() {
    var selectedGoal by remember { mutableStateOf("Muscle Gain") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Header
        Text("Diet Planner 🥗", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        // Sleek Allergy Badge
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF10B981).copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text("Allergy-Safe Mode: 100% Dairy-Free", color = Color(0xFF10B981), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Goal Selection
        Text("Fitness Goal", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            GoalButton("Muscle Gain", selectedGoal == "Muscle Gain", Modifier.weight(1f)) { selectedGoal = it }
            GoalButton("Fat Loss", selectedGoal == "Fat Loss", Modifier.weight(1f)) { selectedGoal = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Multi-Option Recipes
        val breakfast = """
            Option 1: 2 Moong Dal Chillas + Mint Chutney
            Option 2: Tofu Bhurji (100g) with 2 Multigrain Roti
            Option 3: Bowl of Oats with Almond Milk & Chia Seeds
            Option 4: High-Protein Soya Poha with Peanuts
            
            Target: ~25g Protein
        """.trimIndent()

        val lunch = """
            Option 1: Soya Chunk Curry (75g) + 1 Cup Brown Rice
            Option 2: Rajma Masala + Quinoa + Cucumber Salad
            Option 3: Chole (Chickpeas) + 2 Roti + Vegan Curd
            Option 4: Dal Tadka + Bhindi Sabzi + 2 Roti
            
            Target: ~30g Protein
        """.trimIndent()

        val snack = """
            Option 1: Roasted Makhana & Chana (Handful)
            Option 2: Peanut Butter on 2 slices Whole Wheat Toast
            Option 3: Plant-based Protein Shake (with Water/Soy Milk)
            Option 4: Mixed Sprouts Chaat with Lemon
            
            Target: ~15g Protein
        """.trimIndent()

        val dinner = if (selectedGoal == "Fat Loss") {
            """
            Option 1: Grilled Tofu (150g) with Stir-fry Broccoli
            Option 2: Clear Moong Dal Soup + Sautéed Veggies
            Option 3: Soya Kheema (Low Oil) + 1 Roti
            
            Low Carb | High Protein
            """.trimIndent()
        } else {
            """
            Option 1: Dal Makhani (No cream/butter) + 2 Roti
            Option 2: Tofu Peas Masala (Matar Tofu) + Brown Rice
            Option 3: Mixed Dal + Gobi Aloo + 2 Roti
            
            Balanced Muscle Meal
            """.trimIndent()
        }

        // Render Glass Cards
        DietGlassCard("🌅 Breakfast Options", breakfast)
        Spacer(modifier = Modifier.height(16.dp))

        DietGlassCard("🍛 Lunch Options", lunch)
        Spacer(modifier = Modifier.height(16.dp))

        DietGlassCard("⚡ Pre/Post Workout Snack", snack)
        Spacer(modifier = Modifier.height(16.dp))

        DietGlassCard("🍽 Dinner Options", dinner)
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun GoalButton(text: String, isSelected: Boolean, modifier: Modifier, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .height(45.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) AccentBlue else CardDark)
            .clickable { onClick(text) }
            .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = TextWhite, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun DietGlassCard(title: String, content: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(CardDark)
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            Text(title, color = AccentBlue, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(content, color = TextWhite.copy(alpha = 0.85f), fontSize = 15.sp, lineHeight = 24.sp)
        }
    }
}