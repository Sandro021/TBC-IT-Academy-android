package com.example.homework_32.presentation.screen.main.contract

import android.net.Uri

data class MainState(
    val selectedUri: Uri? = null,
    val isUploading: Boolean = false,
    val uploadedUrl: String? = null
)