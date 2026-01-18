package com.example.homework_33.data.mapper

import com.example.homework_33.data.model.EventDto
import com.example.homework_33.domain.model.Event
import com.example.homework_33.domain.model.EventCategory

fun EventDto.toDomain(): Event {
    val cat = when (category?.lowercase()) {
        "party" -> EventCategory.PARTY
        "camping" -> EventCategory.CAMPING
        else -> EventCategory.OTHER
    }
    return Event(
        id = id.orEmpty(),
        title = title.orEmpty(),
        category = cat,
        price = price ?: 0
    )
}