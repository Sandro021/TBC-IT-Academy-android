package com.example.homework_32.presentation.screen.main.contract

import android.net.Uri

sealed class MainEvent {
    data class ImageSelected(val uri: Uri) : MainEvent()
    object OpenPicker : MainEvent()
    object UploadClicked : MainEvent()
}