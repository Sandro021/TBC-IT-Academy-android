package com.example.gymtracker.data.repository

import com.example.gymtracker.data.session.SessionLocalDataSource
import com.example.gymtracker.domain.repository.SessionRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val local: SessionLocalDataSource
) : SessionRepository {
    override fun rememberMeFlow(): Flow<Boolean> = local.rememberMeFlow


    override suspend fun setRememberMe(value: Boolean) {
        local.setRememberMe(value)
    }

    override fun isFirebaseLoggedIn(): Boolean = auth.currentUser != null


    override suspend fun logout() {
        local.setRememberMe(false)
        auth.signOut()
    }
}