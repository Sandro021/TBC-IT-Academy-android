package com.example.homework_20.data.common


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

object HandleResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResultWrapper.Success(apiCall())
            } catch (e: IOException) {
                ResultWrapper.Error("Check your internet connection")
            } catch (e: HttpException) {
                ResultWrapper.Error("Server error ${e.code()}")
            } catch (e: Exception) {
                ResultWrapper.Error(e.message ?: "Unexpected error")
            }
        }
    }
}