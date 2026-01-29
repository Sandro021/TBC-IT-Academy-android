package com.example.homework_37.di


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import com.example.homework_37.data.remote.PostsService
import com.example.homework_37.data.remote.StoriesService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL =
        "https://721659fa-22e5-4fe1-b42e-f482698553b4.mock.pstmn.io/"

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun PostsService(retrofit: Retrofit): PostsService =
        retrofit.create(PostsService::class.java)

    @Provides
    @Singleton
    fun StoriesService(retrofit: Retrofit): StoriesService =
        retrofit.create(StoriesService::class.java)


}