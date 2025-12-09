package com.example.homework_28.presentation.feed.state

import com.example.homework_28.domain.model.Post
import com.example.homework_28.domain.model.Story

data class FeedState(
    val isLoading: Boolean? = null,
    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList(),
    val error: String? = null
)