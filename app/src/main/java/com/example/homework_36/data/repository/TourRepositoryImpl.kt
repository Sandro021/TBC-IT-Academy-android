package com.example.homework_36.data.repository

import com.example.homework_36.data.mapper.toDomain
import com.example.homework_36.data.remote.TourApi
import com.example.homework_36.domain.model.Tour
import com.example.homework_36.domain.repository.TourRepository
import javax.inject.Inject

class TourRepositoryImpl @Inject constructor(
    private val api: TourApi
) : TourRepository {

    override suspend fun getTours(): List<Tour> =
        api.getTours().map { it.toDomain() }


}