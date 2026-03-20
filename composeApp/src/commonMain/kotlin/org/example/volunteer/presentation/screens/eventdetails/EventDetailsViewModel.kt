package org.example.volunteer.presentation.screens.eventdetails

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.Result
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.entity.ApplicationStatus
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.usecase.ApplyForEventUseCase
import org.example.volunteer.domain.usecase.CancelApplicationUseCase
import org.example.volunteer.presentation.BaseViewModel

class EventDetailsViewModel(
    private val eventRepository: EventRepository,
    private val apply: ApplyForEventUseCase,
    private val cancel: CancelApplicationUseCase
): BaseViewModel<EventDetailsUiState, EventDetailsAction, EventDetailsEffect>(
    initialState = EventDetailsUiState()
) {
    override fun onIntent(intent: EventDetailsAction){
        when(intent) {
            EventDetailsAction.Apply -> TODO()
            EventDetailsAction.CancelApplication -> cancelApplication()
            is EventDetailsAction.Load -> load(intent.eventId)
            EventDetailsAction.OpenChat -> openChat()
        }
    }
    private fun openChat(){
        val chatId = state.value.event?.id?:return
        sendEffect(EventDetailsEffect.OpenChat(chatId))
    }
    private fun load(eventId:String){
        viewModelScope.launch {
            updateState { copy(isLoading=true) }
            eventRepository.getEventById(eventId).collect { event ->
                updateState { copy(event = event, isLoading = false) }
            }
        }
    }
    private fun cancelApplication() = viewModelScope.launch {
        val applicationId = state.value.applicationId ?: return@launch
        val eventId = state.value.event?.id ?: return@launch
        updateState { copy(isApplying = true) }
        when (val result = cancel(applicationId, eventId)) {
            is Result.Success -> {
                updateState {
                    copy(
                        isApplying = false,
                        applicationStatus = null,
                    )
                }
                sendEffect(EventDetailsEffect.CancellationSuccess)
            }
            is Result.Error -> {
                updateState { copy(isApplying = false) }
                sendEffect(EventDetailsEffect.ShowError(result.exception.toUiText()))
            }
        }
    }
    private fun apply() = viewModelScope.launch {
        updateState { copy(isApplying = true) }
        when (val result = apply(state.value.event?.id ?: return@launch)) {
            is Result.Success -> {
                updateState {
                    copy(
                        isApplying = false,
                        applicationStatus = ApplicationStatus.PENDING
                    )
                }
                sendEffect(EventDetailsEffect.ApplicationSent)
            }
            is Result.Error -> {
                updateState { copy(isApplying = false) }
                sendEffect(EventDetailsEffect.ShowError(result.exception.toUiText()))
            }
        }
    }
}
