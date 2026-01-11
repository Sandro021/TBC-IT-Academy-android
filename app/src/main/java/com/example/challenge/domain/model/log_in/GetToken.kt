package com.example.challenge.domain.model.log_in

data class GetToken(
    val needsMfa: Boolean = false,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
