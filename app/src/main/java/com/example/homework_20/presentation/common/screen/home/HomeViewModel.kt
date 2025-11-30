package com.example.homework_20.presentation.common.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework_20.data.repository.UsersPagingRepositoryImpl
import com.example.homework_20.domain.model.User
import com.example.homework_20.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: UsersPagingRepositoryImpl,
    private val getUsersUseCase: GetUsersUseCase

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
            try {
                val users = getUsersUseCase()
                _state.value = _state.value.copy(
                    isLoading = false,
                    users = users
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
                _effect.emit(HomeEffect.ShowToast(e.message ?: "Failed to load users"))
            }
        }
    }

    private fun navigateToProfile() {
        viewModelScope.launch {
            _effect.emit(HomeEffect.NavigateToProfile)
        }
    }

    val usersFlow: Flow<PagingData<User>> = repository.getUsersPaging().cachedIn(viewModelScope)

}