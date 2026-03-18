package org.example.volunteer.domain.entity

class AuthResult(
    val userId: String,
    val role: UserRole,
    val accessToken: String,
    val refreshToken: String
)
