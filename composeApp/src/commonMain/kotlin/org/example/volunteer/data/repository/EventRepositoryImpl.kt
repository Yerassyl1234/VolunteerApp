package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.NetworkResult
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

    override suspend fun createEvent(draft: EventDraft): NetworkResult<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEvent(
        id: String,
        draft: EventDraft
    ): NetworkResult<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun archiveEvent(id: String): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }
}