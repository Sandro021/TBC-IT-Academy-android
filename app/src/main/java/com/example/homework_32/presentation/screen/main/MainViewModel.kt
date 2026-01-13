package com.example.homework_32.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.example.homework_32.domain.usecase.UploadImageUseCase
import com.example.homework_32.presentation.common.BaseViewModel
import com.example.homework_32.presentation.screen.main.contract.MainEffect
import com.example.homework_32.presentation.screen.main.contract.MainEvent
import com.example.homework_32.presentation.screen.main.contract.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(
    private val uploadImage: UploadImageUseCase
) : BaseViewModel<MainState, MainEffect, MainEvent>(MainState()) {


    override fun onEvent(event: MainEvent) {

        when (event) {
            is MainEvent.ImageSelected -> {
                updateState { copy(selectedUri = event.uri, uploadedUrl = null) }
            }

            is MainEvent.OpenPicker -> Unit
            is MainEvent.UploadClicked -> upload()
        }
    }

    private fun upload() {
        val uri = state.value.selectedUri

        if (uri == null) {
            sendEffect(MainEffect.ShowMessage("Please select an image first"))
            return
        }
        updateState { copy(isUploading = true) }

        viewModelScope.launch {
            try {
                val url = uploadImage(uri)
                updateState { copy(isUploading = false, uploadedUrl = url) }
                sendEffect(MainEffect.ShowMessage("Uploaded"))
            } catch (e: Exception) {
                updateState { copy(isUploading = false) }
                sendEffect(MainEffect.ShowMessage(e.message ?: "Upload failed"))
            }
        }
    }
}