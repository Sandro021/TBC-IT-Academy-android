package com.example.exam_6.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.exam_6.data.api.WorkspaceApi
import com.example.exam_6.data.mapper.toDomain
import com.example.exam_6.domain.Workspace

class WorksSpacePagingSource(
    private val api: WorkspaceApi
) : PagingSource<Int, Workspace>() {
    override fun getRefreshKey(state: PagingState<Int, Workspace>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Workspace> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val all = api.getWorkspaces().map { it.toDomain() }

            val from = (page - 1) * pageSize
            if (from >= all.size) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = null
                )
            }
            val to = minOf(from + pageSize, all.size)
            val data = all.subList(from, to)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (to < all.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
