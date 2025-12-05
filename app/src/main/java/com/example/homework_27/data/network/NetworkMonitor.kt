package com.example.homework_27.data.network

interface NetworkMonitor {
    suspend fun isOnline() : Boolean
}