package com.example.homework_30.domain.model

data class CategoryDomainModel(
    val id: String,
    val name: String,
    val children: List<CategoryDomainModel>
)
