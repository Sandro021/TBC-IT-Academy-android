package com.example.homework_18.presentation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_18.data.model.UserRequestDto
import com.example.homework_18.data.model.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(UserRequestDto(email, password))
                if (response.isSuccessful) {
                    _username.value = username
                    _status.value = "Registered successfully! Token: ${response.body()?.token}"
                } else {
                    _status.value = "Registration failed!"
                }
            } catch (e: Exception) {
                _status.value = "Error: ${e.message}"
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(UserRequestDto(email, password))
                if (response.isSuccessful) {
                    _status.value = "Login success! Token: ${response.body()?.token}"
                } else {
                    _status.value = "Login failed!"
                }
            } catch (e: Exception) {
                _status.value = "Error: ${e.message}"
            }
        }
    }

    fun logOut() {
        _username.value = ""
        _status.value = ""
    }

    fun deleteUser() {
        _username.value = ""
        _status.value = ""
    }
}