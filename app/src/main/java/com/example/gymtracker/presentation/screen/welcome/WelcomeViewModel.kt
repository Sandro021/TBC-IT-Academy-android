package com.example.gymtracker.presentation.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.domain.usecase.ShouldAutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val shouldAutoLoginUseCase: ShouldAutoLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    fun checkSession() {
        viewModelScope.launch {
            _state.value = shouldAutoLoginUseCase()
        }
    }

    fun navigationHandled() {
        _state.value = false
    }
}