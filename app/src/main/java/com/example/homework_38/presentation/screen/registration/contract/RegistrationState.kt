package com.example.homework_38.presentation.screen.registration.contract

import com.example.homework_38.presentation.screen.registration.model.FieldUiModel

data class RegistrationState(
    val isLoading: Boolean = false,

    val fieldGroups: List<List<FieldUiModel>> = emptyList(),

    val inputValues: Map<Int, String> = emptyMap(),

    val errors: Map<Int, String> = emptyMap()
)
