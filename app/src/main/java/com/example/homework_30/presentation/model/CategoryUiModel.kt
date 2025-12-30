package com.example.homework_30.presentation.model

data class CategoryUiModel(
    val id: String,
    val name: String,
    val dotsCount: Int,
    val children: List<CategoryUiModel>,
    var isExpanded: Boolean = false,
    val level: Int = 0
)
