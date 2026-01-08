package com.example.homework_20.presentation.common.screen.profie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.domain.usecase.ClearSessionUseCase
import com.example.homework_20.domain.usecase.GetProfileEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getProfileEmailUseCase: GetProfileEmailUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile(intent.email)
            is ProfileIntent.Logout -> logOut()
        }
    }

    private fun loadProfile(email: String) {
        viewModelScope.launch {
            val finalEmail = getProfileEmailUseCase(email)
            _state.value = _state.value.copy(email = finalEmail)
        }

    }

    private fun logOut() {
        viewModelScope.launch {
            clearSessionUseCase()
            _state.value = ProfileState(isLoggedOut = true)
        }

    }
}