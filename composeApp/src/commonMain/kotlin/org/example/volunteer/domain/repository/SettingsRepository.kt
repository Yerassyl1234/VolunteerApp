package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.UserRole


interface SettingsRepository {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getRole(): Flow<UserRole?>
    suspend fun saveRole(role: UserRole)
    suspend fun clear()
}