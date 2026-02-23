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

// ✅ BMI CATEGORY
fun getBmiCategory(bmi: Float): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal"
        bmi < 30 -> "Overweight"
        bmi < 35 -> "Obese (Class 1)"
        bmi < 40 -> "Obese (Class 2)"
        else -> "Extreme Obesity"
    }
}

// ✅ BMI ADVICE
fun getBmiAdvice(bmi: Float): String {
    return when {
        bmi < 18.5 ->
            "You are underweight. Increase calorie intake and focus on strength training."

        bmi < 25 ->
            "You are in healthy weight range. Maintain balanced diet and regular workouts."

        bmi < 30 ->
            "You are overweight. Reduce sugar intake and increase cardio activity."

        bmi < 35 ->
            "Obesity level 1. Start structured fat loss program and calorie deficit diet."

        bmi < 40 ->
            "Obesity level 2. Medical consultation recommended."

        else ->
            "Severe obesity. Immediate medical intervention required."
    }
}

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

        // ✅ USE BMI HELPERS CORRECTLY
        profile?.let {

            val category = getBmiCategory(it.bmi)
            val advice = getBmiAdvice(it.bmi)

            InfoCard(
                "BMI",
                String.format("%.1f", it.bmi),
                category
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = advice,
                color = Color.LightGray,
                fontSize = 14.sp
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