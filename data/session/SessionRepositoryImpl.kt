package com.example.homework_20.data.session

import android.content.Context
import androidx.core.content.edit
import com.example.homework_20.domain.repository.SessionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(@ApplicationContext context: Context) :
    SessionRepository {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override suspend fun saveEmail(email: String) {
        prefs.edit { putString("email", email) }
    }

   override suspend fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    override suspend fun isLoggedIn(): Boolean {
        return getEmail() != null
    }

    override suspend fun clearSession() {
        prefs.edit { clear() }
    }
}