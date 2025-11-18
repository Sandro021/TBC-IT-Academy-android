package com.example.homework_20.presentation.common.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository

class WelcomeViewModelFactory(
    private val repo: SessionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WelcomeViewModel(repo) as T
    }
}