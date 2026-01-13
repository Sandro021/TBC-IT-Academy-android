package com.example.homework_32.domain.usecase

import android.net.Uri
import com.example.homework_32.domain.repository.ImageRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(uri: Uri): String = repository.uploadImage(uri)
}