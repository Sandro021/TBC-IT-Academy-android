package com.example.homework_20.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.data.network.AuthApi

class UsersPagingSource(
    private val api: AuthApi
) : PagingSource<Int, UserDto>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val page = params.key ?: 1
            val response = api.getUsers(page)

            if (response.isSuccessful) {
                val body = response.body()
                val users = body?.data ?: emptyList()

                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Error loading users: ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.nextKey?.minus(1)
                ?: state.closestPageToPosition(anchor)?.prevKey?.plus(1)
        }
    }
}