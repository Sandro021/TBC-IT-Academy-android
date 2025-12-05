package com.example.homework_27.data.module

import android.content.Context
import androidx.room.Room
import com.example.homework_27.data.local.AppDatabase
import com.example.homework_27.data.local.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java,
        "user_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideUsersDao(
        db: AppDatabase
    ): UsersDao = db.usersDao()
}