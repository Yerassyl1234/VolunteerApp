package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.repository.EventRepository

class UpdateEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(id:String, draft: EventDraft): Result<Event>{
        return repository.updateEvent(id,draft)
    }
}