package com.example.homework_37.domain.model

data class Post(
    val id: Int,
    val authorName: String,
    val avatarUrl: String?,
    val date: String,
    val content: String?,
    val imageUrls: List<String>,
    val likes: Int,
    val comments: Int
)