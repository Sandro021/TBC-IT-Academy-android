package com.example.homework_20.presentation.common.screen.profie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val repository: SessionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}