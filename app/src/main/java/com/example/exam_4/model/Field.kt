package com.example.exam_4.model

data class Field(
    val fieldId: Int,
    val hint: String,
    val fieldType: String,
    val keyboard: String? = null,
    val required: Boolean = false,
    val isActive: Boolean = true,
    val icon: String? = null
)

