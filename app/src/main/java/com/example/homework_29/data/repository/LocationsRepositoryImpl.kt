package com.example.homework_29.data.repository

import com.example.homework_29.data.LocationsApi
import com.example.homework_29.data.mapper.toDomain
import com.example.homework_29.data.mapper.toEntity
import com.example.homework_29.data.room.LocationDao
import com.example.homework_29.domain.model.LocationItem
import com.example.homework_29.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val api: LocationsApi,
    private val dao: LocationDao
) : LocationsRepository {
    override fun observeLocations(): Flow<List<LocationItem>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun refresh() {
        val remote = api.getLocations()
        dao.upsertAll(remote.map { it.toEntity() })
    }
}