package com.example.data.data.repository

import com.example.data.data.mapper.toDomain
import com.example.data.data.remote.RegistrationApi
import com.example.domain.model.RegistrationField
import com.example.domain.repository.RegistrationRepository
import javax.inject.Inject


class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApi
) : RegistrationRepository {
    override suspend fun getFormStructure(): List<List<RegistrationField>> {
        return api.getRegistrationFields().map { group -> group.map { it.toDomain() } }
    }
}