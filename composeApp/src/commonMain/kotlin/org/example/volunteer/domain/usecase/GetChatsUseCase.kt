package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.Chat
import org.example.volunteer.domain.repository.ChatRepository

class GetChatsUseCase(private val repository: ChatRepository) {
    operator fun invoke(): Flow<List<Chat>>{
        return repository.getChats()
    }
}