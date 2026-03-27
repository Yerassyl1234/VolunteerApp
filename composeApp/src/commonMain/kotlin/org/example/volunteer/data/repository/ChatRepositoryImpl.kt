package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.core.common.networkResultFlowWithRetry
import org.example.volunteer.domain.entity.Chat
import org.example.volunteer.domain.entity.Message
import org.example.volunteer.domain.repository.ChatRepository

class ChatRepositoryImpl : ChatRepository {
    override fun observeMessages(chatId: String): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override fun getChats(): Flow<NetworkResult<List<Chat>>> {
        return networkResultFlowWithRetry(times = 3) {
            //TODO
        }
    }

    override suspend fun sendMessage(
        chatId: String,
        text: String
    ): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }
}