package com.example.formfit.utils

import androidx.compose.ui.graphics.Color

// ✅ BMI Category
fun getBmiCategory(bmi: Float): String = when {
    bmi < 18.5f -> "Underweight"
    bmi < 25f -> "Normal"
    bmi < 30f -> "Overweight"
    bmi < 35f -> "Obese (Class 1)"
    bmi < 40f -> "Obese (Class 2)"
    else -> "Extreme Obesity"
}

// ✅ BMI Health Advice
fun getBmiAdvice(bmi: Float): String = when {
    bmi < 18.5f ->
        "You are underweight. Increase calorie intake and focus on strength training."

    bmi < 25f ->
        "You are in a healthy weight range. Maintain balanced diet and regular workouts."

    bmi < 30f ->
        "You are overweight. Reduce sugar intake and increase cardio activity."

    bmi < 35f ->
        "Obesity Level 1. Start structured fat-loss program and calorie deficit diet."

    bmi < 40f ->
        "Obesity Level 2. Medical consultation recommended along with lifestyle changes."

    else ->
        "Severe obesity. Immediate medical supervision recommended."
}

// ✅ BMI Color Indicator (for UI)
fun getBmiColor(bmi: Float): Color = when {
    bmi < 18.5f -> Color(0xFF00BCD4)  // Cyan
    bmi < 25f -> Color(0xFF4CAF50)   // Green
    bmi < 30f -> Color(0xFFFF9800)   // Orange
    else -> Color(0xFFF44336)        // Red
}