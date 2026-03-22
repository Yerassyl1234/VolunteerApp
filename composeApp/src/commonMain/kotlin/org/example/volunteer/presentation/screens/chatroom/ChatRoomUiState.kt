package org.example.volunteer.presentation.screens.chatroom

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.Message

@Immutable
data class ChatRoomUiState(
    val chatId: String = "",
    val messages: List<Message> = emptyList(),
    val messageText: String = "",
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val canSend get() = messageText.isNotBlank()
}