package com.example.homework_20.presentation.common.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor
    (private val sessionRepository: SessionRepository) :
    ViewModel() {


    private val _effect = MutableSharedFlow<WelcomeEffect>()
    val effect = _effect.asSharedFlow()

    fun processIntent(intent: WelcomeIntent) {
        when (intent) {
            WelcomeIntent.CheckSession -> checkSession()
            WelcomeIntent.ClickLogin -> navigateToLogin()
            WelcomeIntent.ClickRegister -> navigateToRegister()
        }
    }

    private fun checkSession() {
        if (sessionRepository.isLoggedIn()) {
            viewModelScope.launch {
                _effect.emit(WelcomeEffect.NavigateToHome)
            }
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch { _effect.emit(WelcomeEffect.NavigateToLogin) }
    }

    private fun navigateToRegister() {
        viewModelScope.launch { _effect.emit(WelcomeEffect.NavigateToRegister) }
    }

}