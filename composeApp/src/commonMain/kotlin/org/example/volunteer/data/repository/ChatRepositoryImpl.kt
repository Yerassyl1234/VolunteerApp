package org.example.volunteer.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.core.common.safeApiCall
import org.example.volunteer.data.remote.api.ChatApi
import org.example.volunteer.data.remote.dto.MessageResponseDto
import org.example.volunteer.data.remote.dto.toDomain
import org.example.volunteer.domain.entity.Chat
import org.example.volunteer.domain.entity.Message
import org.example.volunteer.domain.repository.ChatRepository
import org.example.volunteer.domain.repository.SettingsRepository

class ChatRepositoryImpl(
    private val api: ChatApi,
    private val client: HttpClient,
    private val settings: SettingsRepository,
    private val json: Json,
) : ChatRepository {

    override fun getChats(): Flow<List<Chat>> = flow {
        emit(api.getChats().map { it.toDomain() })
    }

    override fun observeMessages(chatId: String): Flow<List<Message>> = flow {
        val token = settings.getAccessToken() ?: ""
        val messages = mutableListOf<Message>()

        val session = client.webSocketSession(
            host = "volunteer-app-1usm.onrender.com",
            path = "/ws/chat/$chatId?token=$token",
        )

        session.incoming
            .receiveAsFlow()
            .collect { frame ->
                if (frame is Frame.Text) {
                    try {
                        val dto = json.decodeFromString<MessageResponseDto>(frame.readText())
                        messages.add(dto.toDomain())
                        emit(messages.toList())
                    } catch (_: Exception) {
                        println("WebSocket parse error")
                    }
                }
            }
    }

    override suspend fun sendMessage(chatId: String, text: String): NetworkResult<Unit> =
        safeApiCall {
            api.sendMessage(chatId, text)
            Unit
        }
}