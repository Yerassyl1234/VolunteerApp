package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.AuthResult
import org.example.volunteer.domain.entity.OrganizerProfile
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.entity.VolunteerProfile
import org.example.volunteer.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthResult> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        role: UserRole
    ): Result<AuthResult> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getVolunteerProfile(id: String): Flow<VolunteerProfile> {
        TODO("Not yet implemented")
    }

    override fun getOrganizerProfile(id: String): Flow<OrganizerProfile> {
        TODO("Not yet implemented")
    }
}