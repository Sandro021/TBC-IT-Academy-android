package com.example.homework_36.data.remote

import com.example.homework_36.data.dto.TourDto
import retrofit2.http.GET

interface TourApi {
    @GET("/tours")
    suspend fun getTours(): List<TourDto>
}