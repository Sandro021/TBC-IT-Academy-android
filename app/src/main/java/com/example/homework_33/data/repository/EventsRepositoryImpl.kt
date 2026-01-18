package com.example.homework_33.data.repository

import com.example.homework_33.data.mapper.toDomain
import com.example.homework_33.data.remote.EventsApi
import com.example.homework_33.domain.model.Event
import com.example.homework_33.domain.repository.EventsRepository
import javax.inject.Inject

class EventsRepositoryImpl@Inject constructor(
    private val api: EventsApi
) : EventsRepository {
    override suspend fun getEvents(): List<Event> {
        return api.getEvents().map { it.toDomain() }
    }
}