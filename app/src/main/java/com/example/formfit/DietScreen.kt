package com.example.formfit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun DietScreen() {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Breakfast", "Lunch", "Snack", "Dinner")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1423))
            .padding(16.dp)
    ) {

        Text(
            "Chef Gemini 🍽️",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFF0B1423),
            contentColor = Color.White
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (selectedTab) {
            0 -> MealCard("High Protein Berry Oatmeal", "600 kcal • 40g protein")
            1 -> MealCard("Spicy Tofu & Quinoa Bowl", "750 kcal • 35g protein")
            2 -> MealCard("Greek Yogurt & Nut Parfait", "450 kcal • 25g protein")
            3 -> MealCard("Paneer & Vegetable Stir Fry", "800 kcal • 45g protein")
        }
    }
}
