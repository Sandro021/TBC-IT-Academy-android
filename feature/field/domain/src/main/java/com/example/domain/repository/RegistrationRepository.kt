package com.example.domain.repository

import com.example.domain.model.RegistrationField


interface RegistrationRepository {
    suspend fun getFormStructure(): List<List<RegistrationField>>

}
