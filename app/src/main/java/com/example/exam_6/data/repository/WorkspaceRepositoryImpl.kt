package com.example.exam_6.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.exam_6.data.api.WorkspaceApi
import com.example.exam_6.data.remote.WorksSpacePagingSource
import com.example.exam_6.domain.Workspace
import com.example.exam_6.domain.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkspaceRepositoryImpl @Inject constructor(
    private val api: WorkspaceApi
) : WorkspaceRepository {
    override fun getWorkspaces(): Flow<PagingData<Workspace>> =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { WorksSpacePagingSource(api) }
        ).flow


}