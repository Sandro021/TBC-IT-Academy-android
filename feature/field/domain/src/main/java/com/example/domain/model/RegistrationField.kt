package com.example.domain.model

data class RegistrationField(
    val id: Int,
    val hint: String,
    val type: FieldType,
    val inputType: InputType,
    val isRequired: Boolean,
    val iconUrl: String
)
enum class FieldType { INPUT, CHOOSER }
enum class InputType { TEXT, NUMBER }
