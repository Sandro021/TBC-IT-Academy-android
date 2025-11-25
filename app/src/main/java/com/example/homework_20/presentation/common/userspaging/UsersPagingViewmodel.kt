package com.example.homework_20.presentation.common.userspaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.data.repository.UsersPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UsersPagingViewmodel @Inject constructor(
    repository: UsersPagingRepository
) : ViewModel() {

    val usersFlow: Flow<PagingData<UserDto>> = repository.getUsersPaging().cachedIn(viewModelScope)
}