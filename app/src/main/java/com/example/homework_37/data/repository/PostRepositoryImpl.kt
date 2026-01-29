package com.example.homework_37.data.repository

import com.example.homework_37.data.common.Resource
import com.example.homework_37.data.mapper.toDomain
import com.example.homework_37.data.remote.PostsService
import com.example.homework_37.domain.model.Post
import com.example.homework_37.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostsService
) : PostRepository {
    override fun getFeed(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getFeed()

            val domainData = response.map { it.toDomain() }
            emit(Resource.Success(domainData))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error loading feed"))
        }
    }
}