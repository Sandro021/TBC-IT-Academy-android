package com.example.gymtracker.data.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val REMEMBER_ME = booleanPreferencesKey("remember_me")

    val rememberMeFlow: Flow<Boolean> = dataStore.data
        .map { prefs -> (prefs[REMEMBER_ME] ?: false) }

    suspend fun setRememberMe(value: Boolean) {
        dataStore.edit { it[REMEMBER_ME] = value }
    }
}