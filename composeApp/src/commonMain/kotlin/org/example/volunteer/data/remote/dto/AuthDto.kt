package org.example.volunteer.data.remote.dto

import kotlinx.serialization.Serializable
import org.example.volunteer.domain.entity.AuthResult
import org.example.volunteer.domain.entity.UserRole

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val role: String
)

@Serializable
data class AuthResponseDto(
    val userId: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String
)

fun AuthResponseDto.toDomain() = AuthResult(
    userId = userId,
    role = UserRole.valueOf(role),
    accessToken = accessToken,
    refreshToken = refreshToken
)