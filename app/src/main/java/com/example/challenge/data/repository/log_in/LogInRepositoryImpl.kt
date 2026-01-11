package com.example.challenge.data.repository.log_in

import com.example.challenge.data.common.HandleResponse
import com.example.challenge.data.common.Resource
import com.example.challenge.data.mapper.base.asResource
import com.example.challenge.data.mapper.log_in.toDomain
import com.example.challenge.data.service.log_in.LogInService
import com.example.challenge.domain.model.log_in.GetToken
import com.example.challenge.domain.repository.log_in.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val logInService: LogInService,
    private val handleResponse: HandleResponse,
) : LogInRepository {

    private val bypassLogin = true


    override suspend fun logIn(email: String, password: String): Flow<Resource<GetToken>> {
        if (bypassLogin) {
            return flow {
                emit(Resource.Loading(true))

                val fakeToken = "debug-token-${email.hashCode()}"

                emit(Resource.Success(GetToken(accessToken = fakeToken)))
                emit(Resource.Loading(false))
            }
        }


        return handleResponse.safeApiCall {
            logInService.logIn(email = email, password = password)
        }.asResource {
            it.toDomain()
        }
    }
}