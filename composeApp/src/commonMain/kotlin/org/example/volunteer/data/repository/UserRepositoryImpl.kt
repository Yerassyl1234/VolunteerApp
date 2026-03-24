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

class UserRepositoryImpl(
    private val api: AuthApi,
) : UserRepository {

    override suspend fun login(
        email: String,
        password: String,
    ): NetworkResult<AuthResult> = safeApiCall {
        api.login(email, password).toDomain()
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        role: UserRole,
    ): NetworkResult<AuthResult> = safeApiCall {
        api.register(name, email, password, role.name).toDomain()
    }

    override suspend fun logout(): NetworkResult<Unit> = NetworkResult.Success(Unit)

    override fun getVolunteerProfile(id: String): Flow<VolunteerProfile> = flow {
        // TODO
    }

    override fun getOrganizerProfile(id: String): Flow<OrganizerProfile> = flow {
        // TODO
    }
}