package com.example.homework_27.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_27.data.local.dao.UsersDao
import com.example.homework_27.domain.usecase.ObserveUsersUseCase
import com.example.homework_27.domain.usecase.RefreshUseCase
import com.example.homework_27.presentation.intent.UsersIntent
import com.example.homework_27.presentation.mapper.toPresentation
import com.example.homework_27.presentation.state.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersDao: UsersDao,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state = _state.asStateFlow()

    init {
        observeUsersFromDB()
    }
    fun processIntent(intent : UsersIntent) {
        when(intent) {
            is UsersIntent.InitialLoad -> refresh(fromUser = false)
            is UsersIntent.PullToRefresh -> refresh(fromUser = true)
        }
    }
    private fun observeUsersFromDB() {
        viewModelScope.launch {
            usersDao.observeUsers().collectLatest { entities ->
                _state.update { old ->
                    old.copy(
                        users = entities.map { it.toPresentation() },
                        globalError = null
                    )
                }
            }
        }
    }

    private fun refresh(fromUser: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, globalError = null) }
            val result = refreshUseCase()

            _state.update { it.copy(isLoading = false) }
            result.onFailure { e ->
                val offline = e.message?.contains("Offline", ignoreCase = true) == true
                _state.update { old ->
                    old.copy(
                        isOffline = offline,
                        globalError = if (!offline) e.message ?: "Unknown error" else null
                    )
                }
            }.onSuccess { _state.update { it.copy(isOffline = false) } }
        }
    }
}