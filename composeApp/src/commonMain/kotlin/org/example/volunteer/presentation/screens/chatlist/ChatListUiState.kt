package org.example.volunteer.presentation.screens.chatlist

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.Chat

@Immutable
data class ChatListUiState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val showEmpty get() = !isLoading && chats.isEmpty() && error == null
}