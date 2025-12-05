package com.example.homework_27.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework_27.data.local.dao.UsersDao
import com.example.homework_27.data.local.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}