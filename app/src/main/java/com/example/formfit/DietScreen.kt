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
        // --- HEADER SECTION ---
        Text("Clinical Nutrition 🥗", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("High-Protein Vegetarian Plans", color = AccentBlue, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // --- GOAL TOGGLE ---
        Text("Protocol Objective", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            GoalSelector("Muscle Gain", selectedGoal == "Muscle Gain", Modifier.weight(1f)) { selectedGoal = it }
            GoalSelector("Fat Loss", selectedGoal == "Fat Loss", Modifier.weight(1f)) { selectedGoal = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- DYNAMIC DIET CONTENT ---
        if (selectedGoal == "Muscle Gain") {
            // MUSCLE GAIN PLAN (Caloric Surplus, High Protein with Dairy)
            MealDisplayCard(
                mealTime = "🌅 Breakfast (08:00 AM)",
                macros = "500 kcal • 32g Protein • 55g Carbs • 16g Fat",
                options = """
                    • Option A: 2 Stuffed Paneer Parathas with a bowl of fresh Curd (Yogurt).
                    • Option B: High-Calorie Oats (Oats, 300ml Whole Milk, 1 scoop Whey, Peanut Butter).
                    • Option C: Paneer Bhurji (150g) with 2 slices of toasted whole-wheat bread.
                """.trimIndent()
            )

            MealDisplayCard(
                mealTime = "🍛 Lunch (01:00 PM)",
                macros = "650 kcal • 38g Protein • 75g Carbs • 20g Fat",
                options = """
                    • Option A: Paneer Butter Masala (150g), 2 Roti, 1 cup Rice, and a glass of Chaas (Buttermilk).
                    • Option B: Soya Chunk & Mutter (Peas) Curry, Dal Makhani, and Jeera Rice.
                    • Option C: Rajma (Kidney Beans) with Quinoa and a large bowl of Greek Yogurt.
                """.trimIndent()
            )

            MealDisplayCard(
                mealTime = "⚡ Pre/Post Workout (05:00 PM)",
                macros = "300 kcal • 25g Protein • 35g Carbs • 8g Fat",
                options = """
                    • Option A: 1 Scoop Whey Protein mixed with 250ml Milk and 1 Banana.
                    • Option B: Bowl of Greek Yogurt topped with honey, almonds, and granola.
                    • Option C: Cold Coffee (Milk, Coffee, 1 tsp sugar) and 2 whole-wheat rusk toasts.
                """.trimIndent()
            )

            MealDisplayCard(
                mealTime = "🍽 Dinner (08:30 PM)",
                macros = "450 kcal • 30g Protein • 45g Carbs • 15g Fat",
                options = """
                    • Option A: Kadai Paneer with capsicum, 2 Roti, and a side salad.
                    • Option B: Yellow Dal Tadka, 1 cup Rice, and a glass of warm Turmeric Milk before bed.
                    • Option C: Tofu and Broccoli stir-fry with a light soy-sauce glaze.
                """.trimIndent()
            )
        } else {
            // FAT LOSS PLAN (Caloric Deficit, High Satiety with Dairy)
            MealDisplayCard(
                mealTime = "🌅 Breakfast (08:00 AM)",
                macros = "300 kcal • 22g Protein • 35g Carbs • 8g Fat",
                options = """
                    • Option A: Moong Dal Chilla with 2 tbsp of Low-Fat Curd/Yogurt.
                    • Option B: 1 Scoop Whey Protein in water with a side of mixed berries.
                    • Option C: Overnight Oats made with Skim Milk and Chia seeds.
                """.trimIndent()
            )

            MealDisplayCard(
                mealTime = "🍛 Lunch (01:00 PM)",
                macros = "400 kcal • 28g Protein • 45g Carbs • 12g Fat",
                options = """
                    • Option A: Palak Paneer (made with low-fat paneer & minimal oil) and 1 Roti.
                    • Option B: Large bowl of Sprouts Chaat mixed with 50g roasted paneer cubes.
                    • Option C: 1 bowl of thin Dal, 1 Roti, and a large cucumber raita (yogurt dip).
                """.trimIndent()
            )

            MealDisplayCard(
                mealTime = "⚡ Snack (05:00 PM)",
                macros = "150 kcal • 12g Protein • 15g Carbs • 5g Fat",
                options = """
                    • Option A: 1 large glass of spiced Chaas (Buttermilk) and roasted Makhana.
                    • Option B: 100g Cottage Cheese (Paneer) seasoned with black pepper.
                    • Option C: Black Coffee with a small protein bar.
                """.trimIndent()
            )

            MealDisplayCard(
                mealTime = "🍽 Dinner (08:30 PM)",
                macros = "350 kcal • 25g Protein • 30g Carbs • 10g Fat",
                options = """
                    • Option A: Grilled Paneer Salad (100g Paneer, lettuce, tomatoes, lemon dressing).
                    • Option B: Clear Lentil Soup and a side of sautéed vegetables.
                    • Option C: Small portion of Soya Kheema with a side of steamed broccoli.
                """.trimIndent()
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// --- REUSABLE COMPONENTS FOR DIET SCREEN ---

@Composable
fun GoalSelector(text: String, isSelected: Boolean, modifier: Modifier, onClick: (String) -> Unit) {
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
fun MealDisplayCard(mealTime: String, macros: String, options: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Column {
            Text(mealTime, color = AccentBlue, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(6.dp))

            // Macro Breakdown Tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(macros, color = TextGrey, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(options, color = TextWhite.copy(alpha = 0.85f), fontSize = 14.sp, lineHeight = 24.sp)
        }
    }
}