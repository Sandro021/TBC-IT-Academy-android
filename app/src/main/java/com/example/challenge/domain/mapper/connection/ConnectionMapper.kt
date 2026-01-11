package com.example.challenge.domain.mapper.connection

import com.example.challenge.domain.model.connection.GetConnection
import com.example.challenge.presentation.screen.connection.model.Connection

fun GetConnection.toPresenter() =
    Connection(
        avatar = avatar,
        email = email,
        id = id,
        fullName = fullName
    )