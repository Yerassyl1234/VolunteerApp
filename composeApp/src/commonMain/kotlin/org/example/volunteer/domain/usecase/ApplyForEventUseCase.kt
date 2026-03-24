package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.domain.repository.ApplicationRepository

class ApplyForEventUseCase(private val repository: ApplicationRepository) {
    suspend operator fun invoke(eventId: String): NetworkResult<EventApplication> {
        return repository.apply(eventId)
}}