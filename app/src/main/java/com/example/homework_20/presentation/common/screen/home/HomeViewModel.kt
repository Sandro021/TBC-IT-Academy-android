package com.example.homework_20.presentation.common.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework_20.data.common.ResultWrapper
import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.data.network.UsersRepository
import com.example.homework_20.data.repository.UsersPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: UsersPagingRepository,

    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadUsers -> loadUsers()
            HomeIntent.GoToProfile -> navigateToProfile()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            when (val result = usersRepository.getUsers()) {
                is ResultWrapper.Success -> {
                    _state.value = _state.value.copy(
                        users = result.data
                    )
                }

                is ResultWrapper.Error -> {
                    _effect.emit(HomeEffect.ShowToast(result.message))
                }
            }
        }
    }

    private fun navigateToProfile() {
        viewModelScope.launch {
            _effect.emit(HomeEffect.NavigateToProfile)
        }
    }

    val usersFlow: Flow<PagingData<UserDto>> = repository.getUsersPaging().cachedIn(viewModelScope)

}