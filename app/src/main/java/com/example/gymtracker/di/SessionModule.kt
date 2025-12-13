package com.example.gymtracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.gymtracker.data.repository.SessionRepositoryImpl
import com.example.gymtracker.data.session.SessionLocalDataSource
import com.example.gymtracker.domain.repository.SessionRepository
import com.example.gymtracker.domain.usecase.LogoutUseCase
import com.example.gymtracker.domain.usecase.SetRememberMeUseCase
import com.example.gymtracker.domain.usecase.ShouldAutoLoginUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object SessionModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("session_prefs") }
        )
    }

    @Provides
    @Singleton
    fun provideSessionLocalDataSource(
        dataStore: DataStore<Preferences>
    ): SessionLocalDataSource = SessionLocalDataSource(dataStore)


    @Provides
    @Singleton
    fun provideSessionRepository(
        auth: FirebaseAuth,
        local: SessionLocalDataSource
    ): SessionRepository = SessionRepositoryImpl(auth, local)


    @Provides
    fun provideSetRememberMeUseCase(repo: SessionRepository) = SetRememberMeUseCase(repo)

    @Provides
    fun provideShouldAutoLoginUseCase(repo: SessionRepository) = ShouldAutoLoginUseCase(repo)

    @Provides
    fun provideLogoutUseCase(repo: SessionRepository) = LogoutUseCase(repo)
}