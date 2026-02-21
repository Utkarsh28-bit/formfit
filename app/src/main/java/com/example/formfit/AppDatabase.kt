package com.example.formfit

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutEntity::class, ProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun profileDao(): ProfileDao
}
