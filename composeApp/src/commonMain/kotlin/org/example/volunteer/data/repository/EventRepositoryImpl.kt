package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.repository.EventRepository

class EventRepositoryImpl : EventRepository {
    override fun getEvents(filter: EventFilter): Flow<List<Event>> {
        TODO("Not yet implemented")
    }

    override fun getUrgentEvent(): Flow<Event?> {
        TODO("Not yet implemented")
    }

    override fun getEventById(id: String): Flow<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun createEvent(draft: EventDraft): Result<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEvent(
        id: String,
        draft: EventDraft
    ): Result<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun archiveEvent(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}