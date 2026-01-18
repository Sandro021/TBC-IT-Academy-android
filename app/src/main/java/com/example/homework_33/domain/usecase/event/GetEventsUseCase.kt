package com.example.homework_33.domain.usecase.event

import com.example.homework_33.domain.model.Event
import com.example.homework_33.domain.repository.EventsRepository
import javax.inject.Inject

class GetEventsUseCase@Inject constructor(
    private val repo: EventsRepository) {

    suspend operator fun invoke() : List<Event> = repo.getEvents()
}