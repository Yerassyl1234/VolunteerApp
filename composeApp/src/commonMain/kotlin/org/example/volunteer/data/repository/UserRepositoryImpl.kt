package org.example.volunteer.data.repository

import org.example.volunteer.core.common.safeApiCall
import org.example.volunteer.data.remote.api.AuthApi
import org.example.volunteer.data.remote.dto.toDomain
import org.example.volunteer.domain.entity.AuthResult
import org.example.volunteer.domain.entity.OrganizerProfile
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.entity.VolunteerProfile
import org.example.volunteer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.data.remote.api.ProfileApi
import org.example.volunteer.domain.repository.SettingsRepository

class UserRepositoryImpl(
    private val authApi: AuthApi,
    private val profileApi: ProfileApi,
    private val settings: SettingsRepository,
) : UserRepository {

    override suspend fun login(
        email: String,
        password: String,
    ): NetworkResult<AuthResult> = safeApiCall {
        authApi.login(email, password).toDomain()
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        role: UserRole,
    ): NetworkResult<AuthResult> = safeApiCall {
        authApi.register(name, email, password, role.name).toDomain()
    }

    override suspend fun logout(): NetworkResult<Unit> = safeApiCall {
        val refreshToken = settings.getRefreshToken() ?: ""
        authApi.logout(refreshToken)
        settings.clear()
    }

    override fun getVolunteerProfile(id: String): Flow<VolunteerProfile> = flow {
        emit(profileApi.getVolunteerProfile(id).toDomain())
    }

    override fun getOrganizerProfile(id: String): Flow<OrganizerProfile> = flow {
        emit(profileApi.getOrganizerProfile(id).toDomain())
    }
    override fun getMyVolunteerProfile(): Flow<VolunteerProfile> = flow {
        emit(profileApi.getMyVolunteerProfile().toDomain())
    }
}