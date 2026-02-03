package com.example.presentation.model

data class FieldUiModel(
    val id: Int,
    val label: String,
    val isNumeric: Boolean,
    val isChooser: Boolean,
    val isRequired: Boolean,
    val iconUrl: String,
    val value: String = "",
    val error: String? = null
)