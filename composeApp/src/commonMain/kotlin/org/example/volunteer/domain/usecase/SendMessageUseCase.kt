package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.AppException
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(
        chatId: String,
        text: String,
    ): Result<Unit> {
        if (text.isBlank()) {
            return Result.Error(
                AppException.ValidationException("Сообщение не может быть пустым")
            )
        }
        return repository.sendMessage(chatId, text.trim())
    }
}