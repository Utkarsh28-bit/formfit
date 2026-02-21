package com.example.formfit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val height: Float,
    val weight: Float,
    val allergy: String,
    val email: String,
    val bmi: Float
)
