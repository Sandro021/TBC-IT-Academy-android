package com.example.homework_20.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object RetrofitInstance {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    private val json = Json { ignoreUnknownKeys = true }

    private val contentType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    val api: AuthApi =
        Retrofit.Builder().baseUrl("https://reqres.in/").client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build().create()
}