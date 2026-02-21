package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room

@Composable
fun DashboardScreen(navController: NavController) {

    val context = LocalContext.current

    var profile by remember { mutableStateOf<ProfileEntity?>(null) }

    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fitness_db"
        )
            .allowMainThreadQueries()
            .build()
    }

    LaunchedEffect(Unit) {
        profile = db.profileDao().getProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1423))
            .verticalScroll(rememberScrollState())
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {

        Text(
            "Hi, ${profile?.name ?: "User"} 👋",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        profile?.let {

            InfoCard(
                "BMI",
                String.format("%.1f", it.bmi),
                when {
                    it.bmi < 18.5 -> "Underweight"
                    it.bmi < 25 -> "Normal"
                    it.bmi < 30 -> "Overweight"
                    else -> "Obese"
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoCard(
                "Allergy",
                if (it.allergy.isBlank()) "None" else it.allergy,
                "Diet Adjusted"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        InfoCard("LEVEL", "Intermediate", "2y Exp")

        Spacer(modifier = Modifier.height(12.dp))

        InfoCard("PROTEIN GOAL", "150g", "2g/kg Daily")

        Spacer(modifier = Modifier.height(20.dp))

        GradientCard("Leg Day", "3 Exercises")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("diet") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go To Diet Plan")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Exercise Library",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        exerciseList.forEach { exercise ->
            ExerciseItem(exercise.name) {
                navController.navigate("exercise/${exercise.name}")
            }
        }
    }
}
