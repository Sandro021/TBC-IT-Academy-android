package com.example.exam_6.domain.repository

import androidx.paging.PagingData
import com.example.exam_6.domain.Workspace
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    fun getWorkspaces(): Flow<PagingData<Workspace>>
}