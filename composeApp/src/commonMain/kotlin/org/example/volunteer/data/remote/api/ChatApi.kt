package org.example.volunteer.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.volunteer.data.remote.dto.ChatResponseDto
import org.example.volunteer.data.remote.dto.MessageResponseDto
import org.example.volunteer.data.remote.dto.SendMessageRequestDto

class ChatApi(private val client: HttpClient) {

    suspend fun getChats(): List<ChatResponseDto> =
        client.get("/chats").body()

    suspend fun sendMessage(chatId: String, text: String): MessageResponseDto =
        client.post("/chats/$chatId/messages") {
            contentType(ContentType.Application.Json)
            setBody(SendMessageRequestDto(text))
        }.body()

    suspend fun markAsRead(chatId: String) {
        client.post("/chats/$chatId/read")
    }
}