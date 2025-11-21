package com.example.exam_5.data.dto

enum class MessageType(val rawValue : String) {
    TEXT("text"),
    FILE("file"),
    VOICE("voice");

    companion object {
        fun from(value: String): MessageType {
            return entries.firstOrNull() { it.rawValue == value } ?: TEXT
        }
    }
}