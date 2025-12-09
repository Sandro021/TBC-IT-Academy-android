package com.example.homework_28.presentation.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_28.domain.usecase.GetPostsUseCase
import com.example.homework_28.domain.usecase.GetStoriesUseCase
import com.example.homework_28.presentation.feed.intent.FeedIntent
import com.example.homework_28.presentation.feed.state.FeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FeedState())
    val state = _state.asStateFlow()


    fun processIntent(intent: FeedIntent) {
        when (intent) {
            FeedIntent.Load,
            FeedIntent.Refresh -> loadFeed()
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val storiesDeferred = async { getStoriesUseCase() }
                val postsDeferred = async { getPostsUseCase() }

                val stories = storiesDeferred.await()
                val posts = postsDeferred.await()

                _state.value = _state.value.copy(
                    isLoading = false,
                    posts = posts,
                    stories = stories

                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}