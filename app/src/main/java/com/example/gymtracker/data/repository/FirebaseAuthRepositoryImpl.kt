package com.example.gymtracker.data.repository

import com.example.gymtracker.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}