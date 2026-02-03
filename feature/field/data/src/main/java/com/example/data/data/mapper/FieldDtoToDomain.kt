package com.example.data.data.mapper

import com.example.data.data.dto.FieldDto
import com.example.domain.model.FieldType
import com.example.domain.model.InputType
import com.example.domain.model.RegistrationField


fun FieldDto.toDomain() = RegistrationField(
    id = fieldId,
    hint = hint,
    type = if (fieldType == "input") FieldType.INPUT else FieldType.CHOOSER,
    inputType = if (keyboard == "number") InputType.NUMBER else InputType.TEXT,
    isRequired = required,
    iconUrl = iconUrl
)