package com.example.homework_30.data.common


import com.example.homework_30.domain.common.Resource
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Response body is null")
            }
        } else {
            Resource.Error("Error code: ${response.code()} ${response.message()}")
        }
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Unknown error")
    }
}