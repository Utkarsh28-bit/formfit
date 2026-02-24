package com.example.formfit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.example.formfit.ui.theme.*
import com.example.formfit.utils.getBmiAdvice
import com.example.formfit.utils.getBmiCategory

@Composable
fun DashboardScreen(navController: NavController) {

    val context = LocalContext.current
    var profile by remember { mutableStateOf<ProfileEntity?>(null) }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Push", "Pull", "Legs")

    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fitness_db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    LaunchedEffect(Unit) {
        profile = db.profileDao().getProfile()
    }

    // 🔥 Filter Exercises Properly
    val filteredExercises = if (selectedCategory == "All") {
        exerciseList
    } else {
        exerciseList.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        BackgroundGradientTop,
                        BackgroundGradientBottom
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // 🔥 Header
        Text(
            text = "Hi, ${profile?.name ?: "User"} 👋",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Text(
            text = "Let's crush your goals today.",
            color = TextSecondary
        )

        Spacer(Modifier.height(24.dp))

        // 🔥 Experience Level (Dynamic)
        GlassCard {
            Text("LEVEL", color = TextSecondary, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                profile?.experience ?: "Beginner",
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        // 🔥 Diet Button (Back Added)
        Button(
            onClick = { navController.navigate("diet") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go To Diet Plan")
        }

        Spacer(Modifier.height(28.dp))

        // 🔥 BMI Section
        profile?.let { user ->

            val category = getBmiCategory(user.bmi)
            val advice = getBmiAdvice(user.bmi)

            GlassCard {
                Text("BMI", color = TextSecondary)
                Spacer(Modifier.height(6.dp))
                Text(
                    String.format("%.1f", user.bmi),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGreen
                )
                Text(category, color = TextSecondary)

                Spacer(Modifier.height(8.dp))
                Text(advice, color = TextSecondary, fontSize = 13.sp)
            }

            Spacer(Modifier.height(16.dp))

            GlassCard {
                Text("Allergy", color = TextSecondary)
                Spacer(Modifier.height(6.dp))
                Text(
                    if (user.allergy.isBlank()) "None" else user.allergy,
                    color = TextPrimary
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        // 🔥 Category Tabs
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.forEach { category ->

                val selected = category == selectedCategory

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (selected) AccentGreen else CardDark
                        )
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        category,
                        color = if (selected) Color.Black else TextPrimary
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // 🔥 Exercise Library
        Text(
            "Exercise Library",
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(16.dp))

        filteredExercises.forEach { exercise ->
            ModernExerciseRow(
                name = exercise.name,
                subtitle = "${exercise.category} • ${exercise.reps}"
            ) {
                navController.navigate("exercise/${exercise.name}")
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardDark),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}
