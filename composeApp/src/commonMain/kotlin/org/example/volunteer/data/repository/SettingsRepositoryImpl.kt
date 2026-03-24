package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.repository.SettingsRepository

class SettingsRepositoryImpl : SettingsRepository {
    override suspend fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshToken(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getRole(): Flow<UserRole?> {
        TODO("Not yet implemented")
    }

    override suspend fun saveRole(role: UserRole) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}