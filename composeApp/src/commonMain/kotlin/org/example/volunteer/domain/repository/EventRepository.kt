package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.entity.EventFilter

interface EventRepository {
    fun getEvents(filter: EventFilter): Flow<List<Event>>
    fun getUrgentEvent(): Flow<Event?>
    fun getEventById(id: String): Flow<Event>
    suspend fun createEvent(draft: EventDraft): NetworkResult<Event>
    suspend fun updateEvent(id:String, draft: EventDraft):NetworkResult<Event>
    suspend fun archiveEvent(id:String):NetworkResult<Unit>
}