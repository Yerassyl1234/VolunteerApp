package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.AppException
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.repository.EventRepository

class CreateEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(draft: EventDraft): NetworkResult<Event> {
        if(!draft.isValid){
            return NetworkResult.Error(
                AppException.ValidationException("Заполните все поля")
            )
        }
        return repository.createEvent(draft)
    }
}