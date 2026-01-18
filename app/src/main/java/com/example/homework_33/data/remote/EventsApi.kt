package com.example.homework_33.data.remote

import com.example.homework_33.data.model.EventDto
import retrofit2.http.GET

interface EventsApi {
    @GET("events")
    suspend fun getEvents(): List<EventDto>
}