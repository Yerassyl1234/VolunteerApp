package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.AuthResult
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.OrganizerProfile
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.entity.VolunteerProfile

interface UserRepository {
    suspend fun login(email: String, password: String): Result<AuthResult>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: UserRole,
    ): Result<AuthResult>
    suspend fun logout(): Result<Unit>
    fun getVolunteerProfile(id: String): Flow<VolunteerProfile>
    fun getOrganizerProfile(id: String): Flow<OrganizerProfile>
}