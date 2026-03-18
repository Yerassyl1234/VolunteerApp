package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.OrganizerProfile
import org.example.volunteer.domain.repository.UserRepository

class GetOrganizerProfileUseCase(private val repository: UserRepository) {
    operator fun invoke(id: String): Flow<OrganizerProfile>{
        return repository.getOrganizerProfile(id)
}}