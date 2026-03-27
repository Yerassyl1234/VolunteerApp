package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.core.common.safeApiCall
import org.example.volunteer.data.local.dao.EventDao
import org.example.volunteer.data.local.entity.toEntity
import org.example.volunteer.data.local.entity.toEvent
import org.example.volunteer.data.remote.api.EventApi
import org.example.volunteer.data.remote.dto.toDomain
import org.example.volunteer.data.remote.dto.toRequestDto
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventDraft
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.repository.EventRepository

class EventRepositoryImpl(
    private val api: EventApi,
    private val dao: EventDao,
) : EventRepository {

    override fun getEvents(filter: EventFilter): Flow<List<Event>> {
        val dbFlow = when {
            filter.category != null && filter.query.isNotBlank() ->
                dao.searchByCategoryAndTitle(filter.category.name, filter.query)

            filter.category != null ->
                dao.observeByCategory(filter.category.name)

            filter.query.isNotBlank() ->
                dao.searchByTitle(filter.query)

            else ->
                dao.observeAll()
        }

        return dbFlow
            .map { entities -> entities.map { it.toEvent() } }
            .onStart { refreshEvents(filter) }
    }

    override fun getEventById(id: String): Flow<Event> {
        return dao.observeById(id)
            .onStart { refreshEventById(id) }
            .map { entity ->
                entity?.toEvent() ?: throw NoSuchElementException("Event $id not found")
            }
    }

    override fun getUrgentEvent(): Flow<Event?> {
        return dao.observeUrgent()
            .onStart { refreshUrgentEvent() }
            .map { it?.toEvent() }
    }

    override suspend fun createEvent(draft: EventDraft): NetworkResult<Event> = safeApiCall {
        val event = api.createEvent(draft.toRequestDto()).toDomain()
        dao.insert(event.toEntity())
        event
    }

    override suspend fun updateEvent(id: String, draft: EventDraft): NetworkResult<Event> =
        safeApiCall {
            val event = api.updateEvent(id, draft.toRequestDto()).toDomain()
            dao.insert(event.toEntity())
            event
        }

    override suspend fun archiveEvent(id: String): NetworkResult<Unit> = safeApiCall {
        api.archiveEvent(id)
        dao.deleteById(id)
    }

    private suspend fun refreshEvents(filter: EventFilter) {
        try {
            val fresh = api.getEvents(
                category = filter.category?.name,
                query = filter.query,
            ).map { it.toDomain() }
            dao.insertAll(fresh.map { it.toEntity() })
        } catch (_: Exception) {
            // silent fail
        }
    }

    private suspend fun refreshEventById(id: String) {
        try {
            val event = api.getEventById(id).toDomain()
            dao.insert(event.toEntity())
        } catch (_: Exception) {
            // silent fail
        }
    }

    private suspend fun refreshUrgentEvent() {
        try {
            val event = api.getUrgentEvent().toDomain()
            dao.insert(event.toEntity())
        } catch (_: Exception) {
            //
        }
    }
}