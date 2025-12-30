package com.example.homework_30.presentation.mapper

import com.example.homework_30.domain.model.CategoryDomainModel
import com.example.homework_30.presentation.model.CategoryUiModel

fun CategoryDomainModel.toUi(level: Int = 0): CategoryUiModel {
    val count = this.children.size
    val constrainedCount = if (count > 4) 4 else count

    return CategoryUiModel(
        id = this.id,
        name = this.name,
        dotsCount = constrainedCount,
        level = level,
        children = this.children.map { it.toUi(level + 1) }
    )
}