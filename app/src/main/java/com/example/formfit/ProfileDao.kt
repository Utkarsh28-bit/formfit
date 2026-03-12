package com.example.formfit

import androidx.room.*

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    // ✅ FIX: Added "ORDER BY id DESC" to get your latest login data
    @Query("SELECT * FROM profile_table ORDER BY id DESC LIMIT 1")
    suspend fun getProfile(): ProfileEntity?
}