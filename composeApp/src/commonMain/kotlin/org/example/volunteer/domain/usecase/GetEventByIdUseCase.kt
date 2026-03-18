package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.repository.EventRepository

class GetEventByIdUseCase(private val repository: EventRepository) {
    operator fun invoke(id: String): Flow<Event>{
        return repository.getEventById(id)
    }
}