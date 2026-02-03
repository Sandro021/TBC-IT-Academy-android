package com.example.presentation.mapper

import com.example.domain.model.FieldType
import com.example.domain.model.InputType
import com.example.domain.model.RegistrationField
import com.example.presentation.model.FieldUiModel

fun RegistrationField.toUiModel(): FieldUiModel {
    return FieldUiModel(
        id = id,
        label = hint + if (isRequired) "*" else "",
        isNumeric = inputType == InputType.NUMBER,
        isChooser = type == FieldType.CHOOSER,
        isRequired = isRequired,
        iconUrl = iconUrl
    )
}