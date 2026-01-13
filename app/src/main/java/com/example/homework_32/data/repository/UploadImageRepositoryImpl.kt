package com.example.homework_32.data.repository

import android.net.Uri
import com.example.homework_32.data.FirebaseStorageDataSource
import com.example.homework_32.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val ds: FirebaseStorageDataSource
) : ImageRepository {
    override suspend fun uploadImage(uri: Uri): String = ds.uploadImage(uri)
}