package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.AppException
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(
        chatId: String,
        text: String,
    ): NetworkResult<Unit> {
        if (text.isBlank()) {
            return NetworkResult.Error(
                AppException.ValidationException("Сообщение не может быть пустым")
            )
        }
        return repository.sendMessage(chatId, text.trim())
    }
}