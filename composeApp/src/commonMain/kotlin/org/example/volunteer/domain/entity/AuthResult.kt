package org.example.volunteer.domain.entity

data class AuthResult(
    val userId: String,
    val role: UserRole,
    val accessToken: String,
    val refreshToken: String
)
