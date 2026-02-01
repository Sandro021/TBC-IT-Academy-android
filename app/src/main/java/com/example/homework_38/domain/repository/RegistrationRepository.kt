package com.example.homework_38.domain.repository

import com.example.homework_38.domain.model.RegistrationField

interface RegistrationRepository{
    suspend fun getFormStructure(): List<List<RegistrationField>>
}
