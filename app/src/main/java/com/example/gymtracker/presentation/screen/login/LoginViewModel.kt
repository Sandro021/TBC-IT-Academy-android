package com.example.gymtracker.presentation.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.domain.usecase.LoginUseCase
import com.example.gymtracker.domain.usecase.SeedExerciseGroupsUseCase
import com.example.gymtracker.domain.usecase.SetRememberMeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val setRememberMeUseCase: SetRememberMeUseCase,
    private val seedExerciseGroupsUseCase: SeedExerciseGroupsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _state.update { it.copy(email = intent.value, errorMessage = null) }
            }

            is LoginIntent.PasswordChanged -> {
                _state.update { it.copy(password = intent.value, errorMessage = null) }
            }

            is LoginIntent.RememberMeChanged -> {
                _state.update { it.copy(rememberMe = intent.value) }
            }

            is LoginIntent.ClickLogin -> login()
            is LoginIntent.NavigationHandled -> {
                _state.update { it.copy(navigateToHome = false) }
            }
        }
    }

    private fun login() {
        val current = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val result = loginUseCase(current.email, current.password)

            result.onSuccess {
                Log.d("LOGIN", "Login SUCCESS")
                seedExerciseGroupsUseCase()
                setRememberMeUseCase(current.rememberMe)
                _state.update {
                    it.copy(
                        isLoading = false, navigateToHome = true, errorMessage = null
                    )
                }
            }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Login failed"
                        )
                    }
                }
        }
    }
}