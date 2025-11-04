package com.example.homework_16.message_info

import java.time.*
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter

data class Message(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val dateTime: LocalDateTime = now()
) {
    val formattedTime: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("hh:mm a")
            return dateTime.format(formatter)
        }
}
