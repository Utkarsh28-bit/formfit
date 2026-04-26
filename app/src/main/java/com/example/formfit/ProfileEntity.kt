package com.example.formfit

data class ProfileEntity(
    val name: String = "",
    val height: Float = 0f,
    val weight: Float = 0f,
    val age: Int = 0,
    val experience: String = "",
    val allergy: String = "",
    val email: String = "",
    val bmi: Float = 0f
)