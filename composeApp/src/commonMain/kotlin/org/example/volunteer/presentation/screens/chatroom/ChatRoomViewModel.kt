package org.example.volunteer.presentation.screens.chatroom

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.handle
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.repository.ChatRepository
import org.example.volunteer.domain.usecase.SendMessageUseCase
import org.example.volunteer.presentation.BaseViewModel
import org.example.volunteer.presentation.screens.chatroom.ChatRoomEffect.ShowError

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
        updateState { copy(chatId = chatId) }
        chatRepository.observeMessages(chatId)
            .asNetworkResult()
            .onEach { result ->
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        messages = result.getOrNull()?:messages,
                    )
                }
                result.onError {
                    sendEffect(ShowError(it.toUiText()))
                }
            }
            .launchIn(viewModelScope)
    }

    private fun send() = viewModelScope.launch {
        if (!state.value.canSend) return@launch
        sendMessage(state.value.chatId, state.value.messageText).handle(
            onSuccess = { updateState { copy(messageText = "") } },
            onError = { sendEffect(ShowError(it.toUiText())) }
        )
    }
}