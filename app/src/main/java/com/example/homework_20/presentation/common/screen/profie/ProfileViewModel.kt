package com.example.homework_20.presentation.common.screen.profie

import androidx.lifecycle.ViewModel
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile(intent.email)
            is ProfileIntent.Logout -> logOut()
        }
    }

    private fun loadProfile(email: String) {
        val finalEmail = email.ifEmpty { sessionRepository.getEmail().orEmpty() }
        _state.value = _state.value.copy(email = finalEmail)
    }

    private fun logOut() {
        sessionRepository.clearSession()
        _state.value = ProfileState(isLoggedOut = true)
    }
}