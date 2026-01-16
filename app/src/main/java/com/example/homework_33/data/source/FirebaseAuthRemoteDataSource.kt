package com.example.homework_33.data.source

import com.example.homework_33.data.source.AuthRemoteDataSource
import com.example.homework_33.data.model.UserDto
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRemoteDataSource(private val auth: FirebaseAuth) : AuthRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): UserDto {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val u = result.user ?: error("User is null")
        return UserDto(uid = u.uid, email = u.email)
    }

    override suspend fun register(
        email: String,
        password: String
    ): UserDto {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val u = result.user ?: error("User is null")
        return UserDto(uid = u.uid, email = u.email)
    }

    override fun currentUser(): UserDto? =
        auth.currentUser?.let { UserDto(uid = it.uid, email = it.email) }


    override fun logout() {
        auth.signOut()
    }
}