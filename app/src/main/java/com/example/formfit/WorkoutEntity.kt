package com.example.formfit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val exerciseName: String,
    val sets: Int,
    val date: Long
)

