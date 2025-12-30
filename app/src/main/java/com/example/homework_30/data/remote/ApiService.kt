package com.example.homework_30.data.remote

import com.example.homework_30.data.dto.CategoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/0c08be03-49c2-493b-951c-6ba8a397dc72")
    suspend fun getCategories(@Query("search") query: String? = null): Response<List<CategoryDto>>
}