package com.example.homework_18.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_18.model.UserRequest
import com.example.homework_18.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _status = MutableStateFlow<String>("")
    val status: StateFlow<String> = _status


    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(UserRequest(email, password))
                if (response.isSuccessful) {
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
                val response = repository.loginUser(UserRequest(email, password))
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
}