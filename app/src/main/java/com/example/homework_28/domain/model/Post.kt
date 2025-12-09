package com.example.homework_28.domain.model

data class Post(
    val avatarUrl: String,
    val authorName: String,
    val postDateMillis: Long,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val description: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)