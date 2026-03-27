package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.AuthResult
import org.example.volunteer.domain.entity.OrganizerProfile
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.entity.VolunteerProfile

interface UserRepository {
    suspend fun login(email: String, password: String): NetworkResult<AuthResult>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: UserRole,
    ): NetworkResult<AuthResult>
    suspend fun logout(): NetworkResult<Unit>
    fun getMyVolunteerProfile():Flow<VolunteerProfile>
    fun getVolunteerProfile(id: String): Flow<VolunteerProfile>
    fun getOrganizerProfile(id: String): Flow<OrganizerProfile>
}