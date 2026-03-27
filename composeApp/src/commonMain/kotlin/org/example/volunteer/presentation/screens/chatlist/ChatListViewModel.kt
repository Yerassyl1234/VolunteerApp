package org.example.volunteer.presentation.screens.chatlist

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.repository.ChatRepository
import org.example.volunteer.presentation.BaseViewModel

class ChatListViewModel(
    private val chatRepository: ChatRepository,
) : BaseViewModel<ChatListUiState, ChatListAction, ChatListEffect>(
    initialState = ChatListUiState()
) {
    init {
        onIntent(ChatListAction.LoadInitialData)
    }

    override fun onIntent(intent: ChatListAction) {
        when (intent) {
            ChatListAction.LoadInitialData -> load()
            is ChatListAction.OpenChat -> sendEffect(ChatListEffect.NavigateToChatRoom(intent.chatId))
        }
    }

    private fun load() {
        chatRepository.getChats()
            .asNetworkResult()
            .onEach { result ->
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        chats = result.getOrNull() ?: chats,
                    )
                }
                result.exceptionOrNull()?.let {
                    sendEffect(ChatListEffect.ShowError(it.toUiText()))
                }
            }
            .launchIn(viewModelScope)
    }
}