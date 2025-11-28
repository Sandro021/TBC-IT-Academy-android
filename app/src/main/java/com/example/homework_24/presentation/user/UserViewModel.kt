package com.example.homework_24.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_24.data.datastore.UserDataStore
import com.example.homework_24.data.datastore.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()



    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.FirstNameChanged -> {
                _state.update { it.copy(firstName = intent.value) }
            }

            is UserIntent.LastNameChanged -> {
                _state.update { it.copy(lastName = intent.value) }
            }

            is UserIntent.EmailChanged -> {
                _state.update { it.copy(email = intent.value) }
            }

            is UserIntent.SaveClicked -> saveUser()
            is UserIntent.ReadClicked -> showUsers()
            is UserIntent.DeleteUser -> deleteUsers(intent.id)

        }
    }

    private fun saveUser() {
        val current = _state.value

        if (current.firstName.isBlank() || current.lastName.isBlank() || current.email.isBlank()) {
            _state.update { it.copy(errorMessage = "All fields are required") }
            return
        }
        val newId = (current.users.maxOfOrNull { it.id } ?: 0) + 1

        val newUser = UserInfo(
            id = newId,
            firstName = current.firstName,
            lastName = current.lastName,
            email = current.email
        )
        viewModelScope.launch {
            _state.update { it.copy(errorMessage = null) }
            userDataStore.addNewUser(newUser)
            _state.update {
                it.copy(
                    firstName = "",
                    lastName = "",
                    email = ""
                )
            }
        }
    }

    private fun showUsers() {
        viewModelScope.launch {
            userDataStore.usersFlow.collect { users ->
                _state.update { it.copy(users = users) }
            }
        }
    }

    private fun deleteUsers(id: Int) {
        viewModelScope.launch {
            userDataStore.deleteUser(id)
        }
    }
}