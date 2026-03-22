package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.Chat
import org.example.volunteer.domain.entity.Message
import org.example.volunteer.domain.repository.ChatRepository

class ChatRepositoryImpl : ChatRepository {
    override fun observeMessages(chatId: String): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override fun getChats(): Flow<List<Chat>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(
        chatId: String,
        text: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}