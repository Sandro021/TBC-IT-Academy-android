package com.example.homework_20.presentation.common.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.domain.usecase.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) :
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
        viewModelScope.launch {
            val isLoggedIn = isUserLoggedInUseCase()
            if (isLoggedIn) {
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