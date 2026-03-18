package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.Message
import org.example.volunteer.domain.repository.ChatRepository

class ObserveMessagesUseCase(private val repository: ChatRepository) {
    operator fun invoke(chatId: String): Flow<List<Message>>{
      return repository.observeMessages(chatId)
}}