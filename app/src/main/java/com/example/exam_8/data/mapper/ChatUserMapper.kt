package com.example.exam_8.data.mapper

import com.example.exam_8.data.model.ChatUserDto
import com.example.exam_8.domain.model.ChatUser
import com.example.exam_8.domain.model.MessageType

fun ChatUserDto.toDomain(): ChatUser {
    return ChatUser(
        id = id,
        image = image,
        owner = owner,
        lastMessage = last_message,
        lastActive = last_active,
        unreadMessages = unread_messages,
        isTyping = is_typing,
        lastMessageType = MessageType.fromApi(last_message)
    )

}
