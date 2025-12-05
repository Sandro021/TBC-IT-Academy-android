package com.example.homework_27.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.homework_27.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("SELECT * FROM users ORDER BY lastActiveEpoch DESC")
    fun observeUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}