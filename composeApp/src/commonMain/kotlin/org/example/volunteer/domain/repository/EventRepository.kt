package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.entity.EventFilter

interface EventRepository {
    fun getEvents(filter: EventFilter): Flow<List<Event>>
    fun getUrgentEvent(): Flow<Event?>
    fun getEventById(id: String): Flow<Event>
    suspend fun createEvent(draft: EventDraft):Result<Event>
    suspend fun updateEvent(id:String, draft: EventDraft):Result<Event>
    suspend fun archiveEvent(id:String):Result<Unit>
}