package com.example.exam_4_recrete.model

data class FieldItem(
    val field_id: Int,
    val hint: String,
    val field_type: String,
    val keyboard: String,
    val required: Boolean,
    val is_active: Boolean,
    val icon: String
)
