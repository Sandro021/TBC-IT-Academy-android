package com.example.exam_6.domain.usecase

import androidx.paging.PagingData
import com.example.exam_6.domain.Workspace
import com.example.exam_6.domain.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorksSpaceUseCase @Inject constructor(
    private val repository: WorkspaceRepository
) {
    operator fun invoke(): Flow<PagingData<Workspace>> = repository.getWorkspaces()
}