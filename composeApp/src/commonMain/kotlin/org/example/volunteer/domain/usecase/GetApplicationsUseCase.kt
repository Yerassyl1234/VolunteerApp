package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.domain.repository.ApplicationRepository

class GetApplicationsUseCase(private val repository: ApplicationRepository) {
    operator fun invoke(eventId: String): Flow<List<EventApplication>>{
       return repository.getApplications(eventId)
}}