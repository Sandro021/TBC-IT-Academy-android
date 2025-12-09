package com.example.homework_28.presentation.feed.intent

sealed class FeedIntent {
    data object Load : FeedIntent()
    data object Refresh : FeedIntent()
}