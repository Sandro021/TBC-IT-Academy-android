package com.example.exam_6.presentation.intent

sealed class HomeIntent {
    data object LoadWorkSpaces : HomeIntent()
}