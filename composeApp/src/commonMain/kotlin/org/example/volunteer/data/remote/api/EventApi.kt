package org.example.volunteer.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.volunteer.data.remote.dto.CreateEventRequestDto
import org.example.volunteer.data.remote.dto.EventResponseDto

class EventApi(private val client: HttpClient) {

    suspend fun getEvents(
        category: String? = null,
        query: String? = null
    ): List<EventResponseDto> =
        client.get("/events") {
            category?.let { parameter("category", it) }
            query?.takeIf { it.isNotBlank() }?.let { parameter("query", it) }
        }.body()

    suspend fun getEventById(id: String): EventResponseDto =
        client.get("/events/$id").body()

    suspend fun getUrgentEvent(): EventResponseDto =
        client.get("/events/urgent").body()

    suspend fun createEvent(body: CreateEventRequestDto): EventResponseDto =
        client.post("/events") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    suspend fun updateEvent(
        id: String,
        body: CreateEventRequestDto
    ): EventResponseDto =
        client.put("/events/$id") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    suspend fun archiveEvent(id: String) {
        client.post("/events/$id/archive")
    }
}