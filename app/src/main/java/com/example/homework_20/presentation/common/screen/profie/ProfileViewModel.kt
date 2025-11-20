package com.example.homework_20.presentation.common.screen.profie

import androidx.lifecycle.ViewModel
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(private val sessionRepository: SessionRepository) :
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
        val finalEmail = email.ifEmpty { sessionRepository.getEmail().orEmpty() }
        _state.value = _state.value.copy(email = finalEmail)
    }

    private fun logOut() {
        sessionRepository.clearSession()
        _state.value = ProfileState(isLoggedOut = true)
    }
}