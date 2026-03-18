package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.AppException
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.repository.EventRepository

class CreateEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(draft: EventDraft): Result<Event>{
        if(!draft.isValid){
            return Result.Error(
                AppException.ValidationException("Заполните все поля")
            )
        }
        return repository.createEvent(draft)
    }
}