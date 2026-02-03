package com.example.data.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FieldDto(
    @SerialName("field_id")
    val fieldId: Int,
    @SerialName("hint")
    val hint: String,
    @SerialName("field_type")
    val fieldType: String,
    @SerialName("keyboard")
    val keyboard: String? = null,
    @SerialName("required")
    val required: Boolean = false,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("icon")
    val iconUrl: String
)