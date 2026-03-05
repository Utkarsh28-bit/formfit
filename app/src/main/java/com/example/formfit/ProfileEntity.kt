package com.example.formfit

import androidx.room.Entity
import androidx.room.PrimaryKey

// ✅ ADD tableName = "profile_table"
@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val height: Float,
    val weight: Float,
    val age: Int,           // ✅ Must be here
    val experience: String, // ✅ Must be here
    val allergy: String,
    val email: String,
    val bmi: Float
)