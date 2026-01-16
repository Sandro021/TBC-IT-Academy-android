package com.example.homework_33.data.mapper

import com.example.homework_33.domain.common.AppError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

fun mapFirebaseError(t: Throwable): AppError = when (t) {
    is FirebaseNetworkException -> AppError.Network
    is FirebaseAuthInvalidCredentialsException -> AppError.InvalidCredentials
    is FirebaseAuthUserCollisionException -> AppError.UserAlreadyExists
    else -> AppError.Unknown
}