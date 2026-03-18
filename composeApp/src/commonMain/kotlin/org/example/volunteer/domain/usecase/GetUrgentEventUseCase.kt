package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.repository.EventRepository

class GetUrgentEventUseCase(private val repository: EventRepository) {
    operator fun invoke(): Flow<Event?>{
        return repository.getUrgentEvent()
    }
}