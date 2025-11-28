package com.example.homework_24.presentation.user

sealed class UserIntent {
    data class FirstNameChanged(val value: String) : UserIntent()
    data class LastNameChanged(val value: String) : UserIntent()
    data class EmailChanged(val value: String) : UserIntent()


    object SaveClicked : UserIntent()
    object ReadClicked : UserIntent()

    data class DeleteUser(val id: Int) : UserIntent()
}