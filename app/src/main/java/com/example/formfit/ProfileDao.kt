package com.example.formfit

import androidx.room.*

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    // ✅ Match the tableName defined in ProfileEntity
    @Query("SELECT * FROM profile_table LIMIT 1")
    suspend fun getProfile(): ProfileEntity?
}