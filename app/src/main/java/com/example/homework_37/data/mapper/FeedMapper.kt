package com.example.homework_37.data.mapper

import com.example.homework_37.data.dto.PostDto
import com.example.homework_37.data.dto.StoryDto
import com.example.homework_37.domain.model.Post
import com.example.homework_37.domain.model.Story
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun StoryDto.toDomain() = Story(
    id = id,
    name = title,
    imageUrl = cover
)

fun PostDto.toDomain(): Post {
    val formatter = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
    return Post(
        id = id,
        authorName = "$firstName $lastName",
        avatarUrl = avatar,
        date = formatter.format(Date(postDate)),
        content = postDesc,
        imageUrls = images ?: emptyList(),
        likes = likesCount,
        comments = commentsCount
    )
}