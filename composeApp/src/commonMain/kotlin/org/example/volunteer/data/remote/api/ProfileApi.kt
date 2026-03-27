package org.example.volunteer.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.volunteer.data.remote.dto.OrganizerProfileResponseDto
import org.example.volunteer.data.remote.dto.UpdateProfileRequestDto
import org.example.volunteer.data.remote.dto.VolunteerProfileResponseDto

class ProfileApi(private val client: HttpClient) {

    suspend fun getMyVolunteerProfile(): VolunteerProfileResponseDto =
        client.get("/volunteers/me").body()

    suspend fun getMyOrganizerProfile(): OrganizerProfileResponseDto =
        client.get("/organizers/me").body()

    suspend fun getVolunteerProfile(id: String): VolunteerProfileResponseDto =
        client.get("/volunteers/$id").body()

    suspend fun getOrganizerProfile(id: String): OrganizerProfileResponseDto =
        client.get("/organizers/$id").body()

    suspend fun updateVolunteerProfile(
        name: String? = null,
        avatarUrl: String? = null
    ) {
        client.put("/volunteers/me") {
            contentType(ContentType.Application.Json)
            setBody(UpdateProfileRequestDto(name, avatarUrl))
        }
    }

    suspend fun updateOrganizerProfile(
        name: String? = null,
        avatarUrl: String? = null
    ) {
        client.put("/organizers/me") {
            contentType(ContentType.Application.Json)
            setBody(UpdateProfileRequestDto(name, avatarUrl))
        }
    }
}