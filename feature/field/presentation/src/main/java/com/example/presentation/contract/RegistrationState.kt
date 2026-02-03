package com.example.presentation.contract

import com.example.presentation.model.FieldUiModel

data class RegistrationState(
    val isLoading: Boolean = false,

    val fieldGroups: List<List<FieldUiModel>> = emptyList(),

    val inputValues: Map<Int, String> = emptyMap(),

    val errors: Map<Int, String> = emptyMap()
)
