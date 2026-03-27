package org.example.volunteer.presentation.screens.manageevents

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.handle
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.entity.ApplicationStatus
import org.example.volunteer.domain.repository.ApplicationRepository
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.usecase.AcceptApplicantUseCase
import org.example.volunteer.domain.usecase.RejectApplicantUseCase
import org.example.volunteer.presentation.BaseViewModel

class ManageEventViewModel(
    private val eventRepository: EventRepository,
    private val applicationRepository: ApplicationRepository,
    private val acceptUseCase: AcceptApplicantUseCase,
    private val rejectUseCase: RejectApplicantUseCase,
) : BaseViewModel<ManageEventUiState, ManageEventAction, ManageEventEffect>(
    initialState = ManageEventUiState()
) {
    override fun onIntent(intent: ManageEventAction) {
        when (intent) {
            is ManageEventAction.Load -> load(intent.eventId)
            is ManageEventAction.Accept -> acceptApplicant(intent.applicationId)
            is ManageEventAction.Reject -> rejectApplicant(intent.applicationId)
            is ManageEventAction.OpenChat -> sendEffect(ManageEventEffect.OpenChat(intent.chatId))
            ManageEventAction.ArchiveEvent -> archiveEvent()
        }
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
                    sendEffect(ManageEventEffect.ShowError(it.toUiText()))
                }
            }
            .launchIn(viewModelScope)

        applicationRepository.getApplications(eventId)
            .asNetworkResult()
            .onEach { result ->
                result.getOrNull()?.let { applications ->
                    updateState {
                        copy(
                            pendingApplications = applications.filter { it.status == ApplicationStatus.PENDING },
                            acceptedVolunteers = applications.filter { it.status == ApplicationStatus.ACCEPTED },
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun acceptApplicant(applicationId: String) = viewModelScope.launch {
        updateState { copy(processingId = applicationId) }
        applicationRepository.accept(applicationId).handle(
            onSuccess = {
                val accepted = state.value.pendingApplications
                    .find { it.id == applicationId }
                    ?.copy(status = ApplicationStatus.ACCEPTED)
                updateState {
                    copy(
                        processingId = null,
                        pendingApplications = pendingApplications.filter { it.id != applicationId },
                        acceptedVolunteers = if (accepted != null) acceptedVolunteers + accepted else acceptedVolunteers,
                    )
                }
            },
            onError = {
                updateState { copy(processingId = null) }
                sendEffect(ManageEventEffect.ShowError(it.toUiText()))
            }
        )
    }

    private fun rejectApplicant(applicationId: String) = viewModelScope.launch {
        updateState { copy(processingId = applicationId) }
        rejectUseCase(applicationId).handle(
            onSuccess = {
                updateState {
                    copy(
                        processingId = null,
                        pendingApplications = pendingApplications.filter { it.id != applicationId },
                    )
                }
            },
            onError = {
                updateState { copy(processingId = null) }
                sendEffect(ManageEventEffect.ShowError(it.toUiText()))
            }
        )
    }

    private fun archiveEvent() = viewModelScope.launch {
        val eventId = state.value.event?.id ?: return@launch
        eventRepository.archiveEvent(eventId).handle(
            onSuccess = { sendEffect(ManageEventEffect.NavigateToArchive) },
            onError = { sendEffect(ManageEventEffect.ShowError(it.toUiText())) }
        )
    }
}