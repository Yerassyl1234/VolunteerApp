package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.VolunteerProfile
import org.example.volunteer.domain.repository.UserRepository

class GetVolunteerProfileUseCase(private val repository: UserRepository) {
    operator fun invoke(id: String): Flow<VolunteerProfile> {
      return repository.getVolunteerProfile(id)
    }}