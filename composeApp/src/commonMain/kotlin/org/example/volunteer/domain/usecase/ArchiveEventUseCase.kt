package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.repository.EventRepository

class ArchiveEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(id: String): Result<Unit>{
       return repository.archiveEvent(id)
}}
