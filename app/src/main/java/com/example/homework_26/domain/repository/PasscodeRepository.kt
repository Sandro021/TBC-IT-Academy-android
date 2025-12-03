package com.example.homework_26.domain.repository

interface PasscodeRepository {
    fun getStoredPasscode(): String
}