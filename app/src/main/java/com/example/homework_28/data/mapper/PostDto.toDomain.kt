package com.example.homework_28.data.mapper

import com.example.homework_28.data.model.PostDto
import com.example.homework_28.domain.model.Post

fun PostDto.toDomain() = Post(
    avatarUrl = avatar,
    authorName = "$firstName $lastName",
    postDateMillis = postDate,
    images = images,
    commentsCount = commentsCount,
    likesCount = likesCount,
    description = postDesc,
    canComment = canComment,
    canPostPhoto = canPostPhoto
)