package com.example.homework_32.domain.repository

import android.net.Uri

interface ImageRepository {
    suspend fun uploadImage(uri: Uri): String
}