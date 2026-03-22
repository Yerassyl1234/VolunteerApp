package org.example.volunteer.presentation.screens.chatroom

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.repository.ChatRepository
import org.example.volunteer.domain.usecase.SendMessageUseCase
import org.example.volunteer.presentation.BaseViewModel
import org.example.volunteer.core.common.Result
import org.example.volunteer.core.common.toUiText

class ChatRoomViewModel(
    private val chatRepository: ChatRepository,
    private val sendMessage: SendMessageUseCase,
) : BaseViewModel<ChatRoomUiState, ChatRoomAction, ChatRoomEffect>(
    initialState = ChatRoomUiState()
) {
    override fun onIntent(intent: ChatRoomAction) {
        when (intent) {
            is ChatRoomAction.Load -> load(intent.chatId)
            is ChatRoomAction.MessageChanged -> updateState { copy(messageText = intent.text) }
            ChatRoomAction.SendMessage -> send()
        }
    }

    private fun load(chatId: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, chatId = chatId) }
            chatRepository.observeMessages(chatId).collect { messages ->
                updateState { copy(messages = messages, isLoading = false) }
            }
        }
    }

    private fun send() = viewModelScope.launch {
        if (!state.value.canSend) return@launch
        when (val result = sendMessage(state.value.chatId, state.value.messageText)) {
            is Result.Success -> updateState { copy(messageText = "") }
            is Result.Error -> sendEffect(ChatRoomEffect.ShowError(result.exception.toUiText()))
        }
    }
}