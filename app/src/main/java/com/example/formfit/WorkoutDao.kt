package com.example.formfit

import androidx.room.*

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Query("SELECT * FROM WorkoutEntity")
    suspend fun getAllWorkouts(): List<WorkoutEntity>
}
