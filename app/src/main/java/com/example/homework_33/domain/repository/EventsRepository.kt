package com.example.homework_33.domain.repository

import com.example.homework_33.domain.model.Event

interface EventsRepository {
    suspend fun getEvents(): List<Event>
}