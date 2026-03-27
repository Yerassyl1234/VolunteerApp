package org.example.volunteer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)

@Serializable
data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
)

@Serializable
data class AuthResponseDto(
    val userId: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
)
