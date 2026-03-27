package org.example.volunteer.data.remote.dto

import kotlinx.serialization.Serializable
import org.example.volunteer.domain.entity.AuthResult
import org.example.volunteer.domain.entity.UserRole

fun AuthResponseDto.toDomain() = AuthResult(
    userId = userId,
    role = UserRole.valueOf(role),
    accessToken = accessToken,
    refreshToken = refreshToken
)