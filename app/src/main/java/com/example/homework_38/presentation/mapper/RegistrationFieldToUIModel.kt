package com.example.homework_38.presentation.mapper

import com.example.homework_38.domain.model.FieldType
import com.example.homework_38.domain.model.InputType
import com.example.homework_38.domain.model.RegistrationField
import com.example.homework_38.presentation.screen.registration.model.FieldUiModel

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