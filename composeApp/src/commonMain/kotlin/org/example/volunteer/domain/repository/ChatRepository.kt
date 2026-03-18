package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.Chat
import org.example.volunteer.domain.entity.Message

interface ChatRepository {
    fun observeMessages(chatId: String): Flow<List<Message>>
    fun getChats(): Flow<List<Chat>>
    suspend fun sendMessage(chatId: String, text: String): Result<Unit>
}