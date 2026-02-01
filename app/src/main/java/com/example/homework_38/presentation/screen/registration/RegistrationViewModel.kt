package com.example.homework_38.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_38.domain.usecase.GetRegistrationFormUseCase
import com.example.homework_38.presentation.mapper.toUiModel
import com.example.homework_38.presentation.screen.registration.contract.RegistrationEffect
import com.example.homework_38.presentation.screen.registration.contract.RegistrationEvent
import com.example.homework_38.presentation.screen.registration.contract.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val getRegistrationFormUseCase: GetRegistrationFormUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _effect = Channel<RegistrationEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleEvent(RegistrationEvent.LoadFields)
    }

    fun handleEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.LoadFields -> loadFields()
            is RegistrationEvent.OnValueChange -> updateValue(event.fieldId, event.value)
            is RegistrationEvent.OnRegisterClicked -> validateAndSubmit()
        }
    }

    private fun loadFields() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getRegistrationFormUseCase().fold(
                onSuccess = { groups ->

                    val uiGroups = groups.map { group ->
                        group.map { it.toUiModel() }
                    }
                    _state.update { it.copy(isLoading = false, fieldGroups = uiGroups) }
                },
                onFailure = {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(RegistrationEffect.ShowToast("Failed to load form"))
                }
            )
        }
    }

    private fun updateValue(id: Int, value: String) {

        _state.update {
            val newValues = it.inputValues.toMutableMap().apply { put(id, value) }

            val newErrors = it.errors.toMutableMap().apply { remove(id) }
            it.copy(inputValues = newValues, errors = newErrors)
        }
    }

    private fun validateAndSubmit() {
        val currentState = _state.value
        val newErrors = mutableMapOf<Int, String>()
        val flattenedFields = currentState.fieldGroups.flatten()

        flattenedFields.forEach { field ->
            val value = currentState.inputValues[field.id] ?: ""
            if (field.isRequired && value.isBlank()) {
                newErrors[field.id] = "${field.label.replace("*", "")} is required"
            }
        }

        if (newErrors.isNotEmpty()) {
            _state.update { it.copy(errors = newErrors) }
            viewModelScope.launch { _effect.send(RegistrationEffect.ShowToast("Please fix errors")) }
        } else {

            val finalJson =
                currentState.inputValues.toString()
            viewModelScope.launch {
                _effect.send(RegistrationEffect.NavigateNext(finalJson))
            }
        }
    }
}