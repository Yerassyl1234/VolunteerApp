package org.example.volunteer.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import org.example.volunteer.data.remote.dto.ApplicationResponseDto
import org.example.volunteer.data.remote.dto.ApplicationShortResponseDto

class ApplicationApi(private val client: HttpClient) {

    suspend fun getApplications(eventId: String): List<ApplicationResponseDto> =
        client.get("/events/$eventId/applications").body()

    suspend fun apply(eventId: String): ApplicationShortResponseDto =
        client.post("/events/$eventId/apply").body()

    suspend fun cancel(applicationId: String) {
        client.delete("/applications/$applicationId")
    }

    suspend fun accept(applicationId: String) {
        client.post("/applications/$applicationId/accept")
    }

    suspend fun reject(applicationId: String) {
        client.post("/applications/$applicationId/reject")
    }
}