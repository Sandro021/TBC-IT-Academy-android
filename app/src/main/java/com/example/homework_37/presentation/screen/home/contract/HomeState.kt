package com.example.homework_37.presentation.screen.home.contract

import com.example.homework_37.domain.model.Post
import com.example.homework_37.domain.model.Story

data class HomeState(
    val isStoriesLoading: Boolean = false,
    val isFeedLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList(),
    val error: String? = null
)