package com.example.homework_36.domain.usecase

import com.example.homework_36.domain.model.Tour
import com.example.homework_36.domain.repository.TourRepository
import javax.inject.Inject

class GetToursUseCase @Inject constructor(
    private val repository: TourRepository
) {
    suspend operator fun invoke(): List<Tour> = repository.getTours()
}