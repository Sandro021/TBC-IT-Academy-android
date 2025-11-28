package com.example.homework_24.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.example.homework_24.data.datastore.UserPrefs
import com.example.homework_24.data.datastore.UserPrefsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<UserPrefs> {
        return DataStoreFactory.create(
            serializer = UserPrefsSerializer,
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        ) {
            File(context.filesDir, "user_prefs.pb")
        }
    }
}