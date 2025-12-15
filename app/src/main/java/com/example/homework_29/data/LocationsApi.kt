package com.example.homework_29.data

import com.example.homework_29.data.model.LocationDto
import retrofit2.http.GET

interface LocationsApi {
    @GET("v1/d7c6d734-6080-4045-a196-7da16339b6d7")
    suspend fun getLocations(): List<LocationDto>
}