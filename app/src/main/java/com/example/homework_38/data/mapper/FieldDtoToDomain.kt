package com.example.homework_38.data.mapper

import com.example.homework_38.data.dto.FieldDto
import com.example.homework_38.domain.model.FieldType
import com.example.homework_38.domain.model.InputType
import com.example.homework_38.domain.model.RegistrationField

fun FieldDto.toDomain() = RegistrationField(
    id = fieldId,
    hint = hint,
    type = if (fieldType == "input") FieldType.INPUT else FieldType.CHOOSER,
    inputType = if (keyboard == "number") InputType.NUMBER else InputType.TEXT,
    isRequired = required,
    iconUrl = iconUrl
)