package com.example.homework_30.data.mapper

import com.example.homework_30.data.dto.CategoryDto
import com.example.homework_30.domain.model.CategoryDomainModel

fun CategoryDto.toDomain(): CategoryDomainModel {
    return CategoryDomainModel(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        children = this.children?.map { it.toDomain() } ?: emptyList()
    )
}