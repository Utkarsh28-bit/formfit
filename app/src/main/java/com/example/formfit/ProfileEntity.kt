package com.example.formfit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val height: Float,
    val weight: Float,
    val age: Int,
    val ageCategory: String,
    val allergy: String,
    val email: String,
    val bmi: Float
)