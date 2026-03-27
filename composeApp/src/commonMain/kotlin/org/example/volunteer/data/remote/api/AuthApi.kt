package org.example.volunteer.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.volunteer.data.remote.dto.*

class AuthApi(private val client: HttpClient) {

    suspend fun login(
        email: String,
        password: String
    ): AuthResponseDto =
        client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(email, password))
        }.body()

    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: String
    ): AuthResponseDto =
        client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequestDto(name, email, password, role))
        }.body()

    suspend fun logout(refreshToken: String) {
        client.post("/auth/logout") {
            contentType(ContentType.Application.Json)
            setBody(RefreshRequestDto(refreshToken))
        }
    }

    suspend fun refresh(refreshToken: String): TokenPairResponseDto =
        client.post("/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(RefreshRequestDto(refreshToken))
        }.body()

    suspend fun me(): MeResponseDto =
        client.get("/me").body()
}