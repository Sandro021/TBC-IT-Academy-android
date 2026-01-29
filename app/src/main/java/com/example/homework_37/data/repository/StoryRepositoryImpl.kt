package com.example.homework_37.data.repository

import com.example.homework_37.data.common.Resource
import com.example.homework_37.data.mapper.toDomain
import com.example.homework_37.data.remote.StoriesService
import com.example.homework_37.domain.model.Story
import com.example.homework_37.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val api: StoriesService
) : StoryRepository {
    override fun getStories(): Flow<Resource<List<Story>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getStories()

            val domainData = response.map { it.toDomain() }

            emit(Resource.Success(domainData))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error loading stories"))
        }
    }
}