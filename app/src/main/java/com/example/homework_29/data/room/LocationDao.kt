package com.example.homework_29.data.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface LocationDao {
    @Query("SELECT * FROM locations ORDER BY id ASC")
    fun observeAll(): Flow<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<LocationEntity>)
}