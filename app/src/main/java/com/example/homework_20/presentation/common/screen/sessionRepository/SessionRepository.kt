package com.example.homework_20.presentation.common.screen.sessionRepository

import android.content.Context
import androidx.core.content.edit

class SessionRepository(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveEmail(email: String) {
        prefs.edit { putString("email", email) }
    }

    fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    fun isLoggedIn(): Boolean {
        return getEmail() != null
    }

    fun clearSession() {
        prefs.edit { clear() }
    }
}