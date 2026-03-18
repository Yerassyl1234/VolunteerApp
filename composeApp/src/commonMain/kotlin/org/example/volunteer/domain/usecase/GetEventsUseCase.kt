package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.repository.EventRepository

class GetEventsUseCase(private val repository: EventRepository){
    operator fun invoke(filter: EventFilter): Flow<List<Event>>{
       return repository.getEvents(filter)
}}