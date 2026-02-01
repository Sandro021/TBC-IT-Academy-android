package com.example.homework_38.data.repository

import android.Manifest
import com.example.homework_38.data.mapper.toDomain
import com.example.homework_38.data.remote.RegistrationApi
import com.example.homework_38.domain.model.RegistrationField
import com.example.homework_38.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApi
) : RegistrationRepository {
    override suspend fun getFormStructure(): List<List<RegistrationField>> {
        return api.getRegistrationFields().map { group -> group.map { it.toDomain() } }
    }
}