package org.example.volunteer.presentation.screens.eventdetails

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.handle
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.entity.ApplicationStatus
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.usecase.ApplyForEventUseCase
import org.example.volunteer.domain.usecase.CancelApplicationUseCase
import org.example.volunteer.presentation.BaseViewModel

class EventDetailsViewModel(
    private val eventRepository: EventRepository,
    private val applyUseCase: ApplyForEventUseCase,
    private val cancelUseCase: CancelApplicationUseCase,
) : BaseViewModel<EventDetailsUiState, EventDetailsAction, EventDetailsEffect>(
    initialState = EventDetailsUiState()
) {
    override fun onIntent(intent: EventDetailsAction) {
        when (intent) {
            EventDetailsAction.Apply -> apply()
            EventDetailsAction.CancelApplication -> cancelApplication()
            is EventDetailsAction.Load -> load(intent.eventId)
            EventDetailsAction.OpenChat -> openChat()
        }
    }

    private fun openChat() {
        val chatId = state.value.event?.id ?: return
        sendEffect(EventDetailsEffect.OpenChat(chatId))
    }

    private fun load(eventId: String) {
        eventRepository.getEventById(eventId)
            .asNetworkResult()
            .onEach { result ->
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        event = result.getOrNull()?:event,
                    )
                }
                result.onError {
                    sendEffect(EventDetailsEffect.ShowError(it.toUiText()))
                }
            }
            .launchIn(viewModelScope)
    }

    private fun cancelApplication() = viewModelScope.launch {
        val applicationId = state.value.applicationId ?: return@launch
        val eventId = state.value.event?.id ?: return@launch
        updateState { copy(isApplying = true) }
        cancelUseCase(applicationId, eventId).handle(
            onSuccess = {
                updateState { copy(isApplying = false, applicationStatus = null) }
                sendEffect(EventDetailsEffect.CancellationSuccess)
            },
            onError = {
                updateState { copy(isApplying = false) }
                sendEffect(EventDetailsEffect.ShowError(it.toUiText()))
            }
        )
    }

    private fun apply() = viewModelScope.launch {
        updateState { copy(isApplying = true) }
        applyUseCase(state.value.event?.id ?: return@launch).handle(
            onSuccess = {
                updateState { copy(isApplying = false, applicationStatus = ApplicationStatus.PENDING) }
                sendEffect(EventDetailsEffect.ApplicationSent)
            },
            onError = {
                updateState { copy(isApplying = false) }
                sendEffect(EventDetailsEffect.ShowError(it.toUiText()))
            }
        )
    }
}