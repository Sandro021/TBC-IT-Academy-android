package com.example.exam_6.presentation.state

import androidx.paging.PagingData
import com.example.exam_6.domain.Workspace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val workspaces: Flow<PagingData<Workspace>> = emptyFlow()
)