package org.example.volunteer.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.volunteer.data.remote.dto.AIGenerateRequestDto
import org.example.volunteer.data.remote.dto.EventDraftResponseDto

class AIApi(private val client: HttpClient) {

    suspend fun generateEvent(prompt: String): EventDraftResponseDto =
        client.post("/ai/generate-event") {
            contentType(ContentType.Application.Json)
            setBody(AIGenerateRequestDto(prompt))
        }.body()
}