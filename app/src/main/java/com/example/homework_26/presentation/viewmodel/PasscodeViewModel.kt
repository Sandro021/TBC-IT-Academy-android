package com.example.homework_26.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.homework_26.domain.usecase.ValidatePasscodeUseCase
import com.example.homework_26.presentation.intent.PasscodeIntent
import com.example.homework_26.presentation.state.PasscodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor(
    private val validatePasscodeUseCase: ValidatePasscodeUseCase
) : ViewModel() {

    companion object {
        const val PASSCODE_LENGTH = 4
    }

    private val _state = MutableStateFlow(PasscodeState())
    val state = _state.asStateFlow()


    fun processIntent(intent: PasscodeIntent) {
        when (intent) {
            is PasscodeIntent.NumberClicked -> onNumberClick(intent.digit)
            PasscodeIntent.DeleteClicked -> onDelete()
        }
    }

    private fun onNumberClick(digit: Int) {
        val current = _state.value.enteredDigits
        if (current.length >= PASSCODE_LENGTH) return

        val newDigits = current + digit.toString()

        _state.update {
            it.copy(
                enteredDigits = newDigits,
                isSuccess = null,
                errorMessage = null

            )
        }
        if (newDigits.length == PASSCODE_LENGTH) {
            validate(newDigits)
        }

    }

    private fun onDelete() {
        val current = _state.value.enteredDigits
        if (current.isEmpty()) return

        _state.update {
            it.copy(
                enteredDigits = current.dropLast(1),
                isSuccess = null,
                errorMessage = null
            )
        }
    }

    private fun validate(input: String) {
        val success = validatePasscodeUseCase(input)
        if (success) {
            _state.update {
                it.copy(
                    isSuccess = true,
                    errorMessage = null
                )
            }
        } else {
            _state.update {
                it.copy(
                    enteredDigits = "",
                    isSuccess = false,
                    errorMessage = "Incorrect passcode"
                )
            }
        }
    }
}